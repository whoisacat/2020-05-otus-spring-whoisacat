package ru.otus.spring.integration.metabola;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.ButterflyEgg;
import ru.otus.spring.integration.domain.Caterpillar;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CaterpillarService {

    public Caterpillar eat(Caterpillar caterpillar) throws Exception {
        byte times = (byte) RandomUtils.nextInt(1,5);
        for (int i = 0; i < times; i++) {
            System.out.println("Caterpillar " + caterpillar.getSerialNumber() + " starting eating ");
            caterpillar.setSize(caterpillar.getSize() + 1);
            System.out.println("Caterpillar " + caterpillar.getSerialNumber() + " stopped eating, size = " +
                               caterpillar.getSize());
        }
        return caterpillar;
    }

    public Collection<Caterpillar> filter(Collection<Caterpillar> caterpillars) {
        return caterpillars.stream().filter(c -> c.getSize() > 2).collect(Collectors.toList());
    }
}
