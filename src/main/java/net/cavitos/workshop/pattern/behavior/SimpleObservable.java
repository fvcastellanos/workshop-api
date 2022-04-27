package net.cavitos.workshop.pattern.behavior;

import com.google.common.collect.Lists;

import java.util.List;

public abstract class SimpleObservable implements Observable {

    private final List<Observer> observers = Lists.newArrayList();

    @Override
    public void registerObserver(Observer observer) {

        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {

        observers.remove(observer);
    }

    @Override
    public abstract void notifyObservers();
}
