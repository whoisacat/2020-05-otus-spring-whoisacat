package ru.otus.spring.integration.metabola;

import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.Butterfly;
import ru.otus.spring.integration.domain.ButterflyEgg;
import ru.otus.spring.integration.domain.Caterpillar;

@Service
public class EggService {

    public Caterpillar hutch(ButterflyEgg butterflyEgg) throws Exception {
        System.out.println("Hutching egg " + butterflyEgg.getSerialNumber());
        Thread.sleep(30);
        System.out.println("Hutching butterfly from egg " + butterflyEgg.getSerialNumber() + " done");
        return new Caterpillar(butterflyEgg.getSerialNumber());
    }
}
