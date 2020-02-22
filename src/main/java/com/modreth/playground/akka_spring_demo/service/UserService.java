package com.modreth.playground.akka_spring_demo.service;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Props;
import akka.actor.typed.Scheduler;
import akka.actor.typed.javadsl.AskPattern;
import com.modreth.playground.akka_spring_demo.actor.UserActor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

@Service
@Slf4j
public class UserService {

    /**
     * ActorSystem guardian to bootstrap actor messages
     */
    private final ActorSystem<UserActor.CommandMessage> actorSystem;

    /**
     * Instance reference of {@link UserActor}
     */
    private final ActorRef<UserActor.CommandMessage> userActor;

    /**
     * instance of {@link Scheduler} for scheduling actor tasks
     */
    private final Scheduler scheduler;

    /**
     * Duration of request
     */
    private final Duration askTimeout;


    /**
     * Constructor
     */
    public UserService() {
        this.actorSystem = ActorSystem.create(UserActor.createDefaultBehavior(), "userActorSystem");
        this.scheduler = this.actorSystem.scheduler();
        this.askTimeout = Duration.ofSeconds(3);
        this.userActor = this.actorSystem.systemActorOf(UserActor.createDefaultBehavior(), "userActor",
                Props.empty());
    }

    /**
     * Method to retrieve information of users thought Actor Models.
     * @return String with user information.
     */
    public String getUserInformation(){
        getUser("mordreth").whenComplete((getUserResponse, throwable) -> {
            log.info("User information found: [{}]", getUserResponse.userInformation.get());
        });
        return null;
    }

    /**
     * Completable stage to async execute Actor message
     * @param name of user to be searched
     * @return an instance of {@link CompletionStage}
     */
    private CompletionStage<UserActor.GetUser.GetUserResponse> getUser(String name) {
        return AskPattern.ask(userActor, ref -> new UserActor.GetUser(name, ref), askTimeout, scheduler);
    }
}
