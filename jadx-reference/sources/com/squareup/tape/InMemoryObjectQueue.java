package com.squareup.tape;

import com.squareup.tape.ObjectQueue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/* loaded from: classes.dex */
public class InMemoryObjectQueue<T> implements ObjectQueue<T> {
    private ObjectQueue.Listener<T> listener;
    private final Queue<T> tasks = new LinkedList();

    @Override // com.squareup.tape.ObjectQueue
    public void add(T t) {
        this.tasks.add(t);
        ObjectQueue.Listener<T> listener = this.listener;
        if (listener != null) {
            listener.onAdd(this, t);
        }
    }

    @Override // com.squareup.tape.ObjectQueue
    public T peek() {
        return this.tasks.peek();
    }

    @Override // com.squareup.tape.ObjectQueue
    public int size() {
        return this.tasks.size();
    }

    @Override // com.squareup.tape.ObjectQueue
    public void remove() {
        this.tasks.remove();
        ObjectQueue.Listener<T> listener = this.listener;
        if (listener != null) {
            listener.onRemove(this);
        }
    }

    @Override // com.squareup.tape.ObjectQueue
    public void setListener(ObjectQueue.Listener<T> listener) {
        if (listener != null) {
            Iterator<T> it = this.tasks.iterator();
            while (it.hasNext()) {
                listener.onAdd(this, it.next());
            }
        }
        this.listener = listener;
    }
}
