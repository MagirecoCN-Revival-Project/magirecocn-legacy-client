package com.squareup.tape;

import com.squareup.tape.ObjectQueue;
import com.squareup.tape.Task;

/* loaded from: classes.dex */
public class TaskQueue<T extends Task> implements ObjectQueue<T> {
    private final ObjectQueue<T> delegate;
    private final TaskInjector<T> taskInjector;

    public TaskQueue(ObjectQueue<T> objectQueue) {
        this(objectQueue, null);
    }

    public TaskQueue(ObjectQueue<T> objectQueue, TaskInjector<T> taskInjector) {
        this.delegate = objectQueue;
        this.taskInjector = taskInjector;
    }

    /* JADX DEBUG: Method merged with bridge method: peek()Ljava/lang/Object; */
    @Override // com.squareup.tape.ObjectQueue
    public T peek() {
        TaskInjector<T> taskInjector;
        T peek = this.delegate.peek();
        if (peek != null && (taskInjector = this.taskInjector) != null) {
            taskInjector.injectMembers(peek);
        }
        return peek;
    }

    @Override // com.squareup.tape.ObjectQueue
    public int size() {
        return this.delegate.size();
    }

    /* JADX DEBUG: Method merged with bridge method: add(Ljava/lang/Object;)V */
    @Override // com.squareup.tape.ObjectQueue
    public void add(T t) {
        this.delegate.add(t);
    }

    @Override // com.squareup.tape.ObjectQueue
    public void remove() {
        this.delegate.remove();
    }

    @Override // com.squareup.tape.ObjectQueue
    public void setListener(final ObjectQueue.Listener<T> listener) {
        if (listener != null) {
            this.delegate.setListener(new ObjectQueue.Listener<T>() { // from class: com.squareup.tape.TaskQueue.1
                /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: com.squareup.tape.TaskQueue$1 */
                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.squareup.tape.ObjectQueue.Listener
                public /* bridge */ /* synthetic */ void onAdd(ObjectQueue objectQueue, Object obj) {
                    onAdd((ObjectQueue<ObjectQueue>) objectQueue, (ObjectQueue) obj);
                }

                public void onAdd(ObjectQueue<T> objectQueue, T t) {
                    listener.onAdd(TaskQueue.this, t);
                }

                @Override // com.squareup.tape.ObjectQueue.Listener
                public void onRemove(ObjectQueue<T> objectQueue) {
                    listener.onRemove(TaskQueue.this);
                }
            });
        } else {
            this.delegate.setListener(null);
        }
    }
}
