package com.whoisacat.edu.book.batch.catalogue.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class ApplicationEventsCommands{

    private final JobLauncher jobLauncher;
    private final Job importCatalogueJob;


    private String userName;

    public ApplicationEventsCommands(JobLauncher jobLauncher, Job importCatalogueJob) {
        this.jobLauncher = jobLauncher;
        this.importCatalogueJob = importCatalogueJob;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(String userName) {
        if(!userName.isEmpty()){
            this.userName = userName;
            return String.format("Добро пожаловать: %s",userName);
        } else return "Нужно представиться";
    }

    @ShellMethod(value = "unLogin command", key = {"u","ulogin"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String ulogin(){
        String user = this.userName;
        this.userName = null;
        return String.format("Досвидания: %s",user);
    }

    private Availability isPublishEventCommandAvailable() {
        return userName == null? Availability.unavailable("Сначала залогиньтесь"): Availability.available();
    }

    @ShellMethod(value = "Начать миграцию", key = {"sm","start_migration"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public void countAuthors() throws Exception {
        JobExecution execution = jobLauncher.run(importCatalogueJob, new JobParametersBuilder().toJobParameters());
        System.out.println(execution);
    }
}
