package com.modreth.playground.akka_spring_demo.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.util.Optional;

public class UserActor extends AbstractBehavior<UserActor.CommandMessage> {

    /**
     * Message Contract between actors
     */
    public interface CommandMessage {}

    /**
     * Message to obtain requested information
     */
    public final static class GetUser implements CommandMessage {
        public final String name;
        public final ActorRef<GetUserResponse> replyTo;
        public GetUser(String name, ActorRef<GetUserResponse> replyTo) {
            this.name = name;
            this.replyTo = replyTo;
        }

        /**
         * Response for {@link GetUser}
         */
        public final static class GetUserResponse {
            public final Optional<String> userInformation;
            public GetUserResponse(Optional<String> userInformation) { this.userInformation = userInformation; }
        }
    }

    /**
     * Creates Default behaviour for {@link UserActor}
     * @return an instance of {@link Behavior}
     */
    public static Behavior<CommandMessage> createDefaultBehavior() {
        return Behaviors.setup(UserActor::new);
    }

    /**
     * Actor constructor
     * @param actorContext, Context in which a behaviour is executed
     */
    private UserActor(ActorContext<CommandMessage> actorContext){
        super(actorContext);
    }

    @Override
    public Receive<CommandMessage> createReceive() {
        return newReceiveBuilder().onMessage(GetUser.class, this::onGetUser).build();
    }

    /** Handlers for messages **/

    /**
     * Expected behaviour for a message of type {@link GetUser}
     * @param getUserCommand
     * @return
     */
    private Behavior<CommandMessage> onGetUser(GetUser getUserCommand) {
        getUserCommand.replyTo.tell(new GetUser.GetUserResponse(Optional.of("{\"name\":\"Mordreth\"}")));
        return this;
    }

}
