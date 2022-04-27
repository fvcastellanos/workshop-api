package net.cavitos.workshop.pattern.behavior;

public interface Observer {

    <T> void update(T context);
}
