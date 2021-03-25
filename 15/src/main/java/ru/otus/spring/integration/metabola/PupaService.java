package ru.otus.spring.integration.metabola;

import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.Butterfly;
import ru.otus.spring.integration.domain.Caterpillar;
import ru.otus.spring.integration.domain.Pupa;

@Service
public class PupaService {

    public Pupa pupate(Caterpillar caterpillar) throws Exception {
        System.out.println("Pupation " + caterpillar.getSerialNumber());
        Thread.sleep(3000);
        System.out.println("Pupation from caterpillar " + caterpillar.getSerialNumber() + " done");
        return new Pupa(caterpillar.getSerialNumber());
    }

    public Butterfly hutch(Pupa pupa) throws Exception {
        System.out.println("Hutching pupa " + pupa.getSerialNumber());
        Thread.sleep(30);
        System.out.println("Hutching butterfly from pupa " + pupa.getSerialNumber() + " done");
        return new Butterfly("Butterfly from pupa " + pupa.getSerialNumber());
    }
}
