package ru.otus.spring.integration;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.integration.domain.Butterfly;
import ru.otus.spring.integration.domain.ButterflyEgg;
import ru.otus.spring.integration.domain.Caterpillar;
import ru.otus.spring.integration.metabola.CaterpillarService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@IntegrationComponentScan
@ComponentScan
@Configuration
@EnableIntegration
public class ButterflyLifeCycle {
    private static int prevSerialNumber = 0;

    @Autowired private CaterpillarService caterpillarService;

    @MessagingGateway
    public interface ButterflyEggGateway {

        @Gateway(requestChannel = "eggsChannel", replyChannel = "butterflyChannel")
        Collection<Butterfly> process(Collection<ButterflyEgg> butterflyEgg);
    }

    @Bean
    public QueueChannel eggsChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel butterflyChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(20).get();
    }

    @Bean
    public IntegrationFlow caterpillarFlow() {
        return IntegrationFlows.from("eggsChannel")
                               .split()
                               .handle("eggService", "hutch")
                               .handle("caterpillarService", "eat")
                               .aggregate()
                               .handle("caterpillarService", "filter")
                               .channel("caterpillarChannel")
                               .get();
    }

    @Bean
    public IntegrationFlow pupaFlow() {
        return IntegrationFlows.from("caterpillarChannel")
                               .filter(filterPupaeByButchSize())
                               .split()
                               .handle("pupaService", "pupate")
                               .aggregate()
                               .channel("pupaChannel")
                               .get();
    }

    private GenericSelector<List<Caterpillar>> filterPupaeByButchSize() {
        return pupae -> pupae.size() > 10;
    }

    @Bean
    public IntegrationFlow butterflyFlow() {
        return IntegrationFlows.from("pupaChannel")
                               .split()
                               .handle("pupaService", "hutch")
                               .aggregate()
                               .channel("butterflyChannel")
                               .get();
    }

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(ButterflyLifeCycle.class);

        // here we works with cafe using interface
        ButterflyEggGateway butterflyEggGateway = ctx.getBean(ButterflyEggGateway.class);

        while ( true ) {
            Thread.sleep( 4000 );

            ForkJoinPool pool = ForkJoinPool.commonPool();
            pool.execute( () -> {
                Collection<ButterflyEgg> eggs = generateButterfliesEggs();
                System.out.println("New eggs: " +
                                   eggs.stream()
                                        .map(ButterflyEgg::getSerialNumber)
                                        .map(serial -> Integer.toString(serial))
                                        .collect(Collectors.joining(",")) + ", size = " + eggs.size());
                Collection<Butterfly> butterflies = butterflyEggGateway.process( eggs );
                System.out.println(
                        "Eggs chunk size = " + eggs.size() + ", butterflies chunk size = " + butterflies.size()
                        + ". Returned butterfly: " + butterflies
                                .stream()
                                .map(Butterfly::getName)
                                .collect(Collectors.joining(",")));
            } );
        }
    }

    private static ButterflyEgg generateButterfliesEgg() {
        return new ButterflyEgg(++prevSerialNumber);
    }

    private static Collection<ButterflyEgg> generateButterfliesEggs() {
        List<ButterflyEgg> items = new ArrayList<>();
        for ( int i = 0; i < RandomUtils.nextInt( 20, 50 ); ++ i ) {
            items.add( generateButterfliesEgg() );
        }
        return items;
    }
}
