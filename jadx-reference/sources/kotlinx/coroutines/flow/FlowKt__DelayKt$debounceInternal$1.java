package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.selects.SelectInstance;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: Add missing generic type declarations: [T] */
/* compiled from: Delay.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u008a@"}, d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;", "downstream", "Lkotlinx/coroutines/flow/FlowCollector;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1", f = "Delay.kt", i = {0, 0, 0, 0, 1, 1, 1, 1}, l = {222, 355}, m = "invokeSuspend", n = {"downstream", "values", "lastValue", "timeoutMillis", "downstream", "values", "lastValue", "timeoutMillis"}, s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3"})
/* loaded from: classes.dex */
public final class FlowKt__DelayKt$debounceInternal$1<T> extends SuspendLambda implements Function3<CoroutineScope, FlowCollector<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow<T> $this_debounceInternal;
    final /* synthetic */ Function1<T, Long> $timeoutMillisSelector;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: kotlin.jvm.functions.Function1<? super T, java.lang.Long> */
    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: kotlinx.coroutines.flow.Flow<? extends T> */
    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FlowKt__DelayKt$debounceInternal$1(Function1<? super T, Long> function1, Flow<? extends T> flow, Continuation<? super FlowKt__DelayKt$debounceInternal$1> continuation) {
        super(3, continuation);
        this.$timeoutMillisSelector = function1;
        this.$this_debounceInternal = flow;
    }

    /* JADX DEBUG: Method merged with bridge method: invoke(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(CoroutineScope coroutineScope, FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        FlowKt__DelayKt$debounceInternal$1 flowKt__DelayKt$debounceInternal$1 = new FlowKt__DelayKt$debounceInternal$1(this.$timeoutMillisSelector, this.$this_debounceInternal, continuation);
        flowKt__DelayKt$debounceInternal$1.L$0 = coroutineScope;
        flowKt__DelayKt$debounceInternal$1.L$1 = flowCollector;
        return flowKt__DelayKt$debounceInternal$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:9|(5:11|(1:13)|14|(1:16)(1:30)|(2:28|29)(2:18|(5:20|(1:22)|23|(1:25)|27)))|31|32|(3:34|(1:42)(1:38)|(2:40|41))|43|44|45|(1:47)|48|49|(1:51)|(1:53)(4:54|6|7|(2:58|59)(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x012c, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x012d, code lost:
    
        r14.handleBuilderException(r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0111 A[Catch: all -> 0x012c, TryCatch #0 {all -> 0x012c, blocks: (B:45:0x010a, B:47:0x0111, B:48:0x011d), top: B:44:0x010a }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x013f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x007a  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:54:0x0140 -> B:6:0x0144). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        FlowKt__DelayKt$debounceInternal$1<T> flowKt__DelayKt$debounceInternal$1;
        ReceiveChannel produce$default;
        Ref.ObjectRef objectRef;
        Ref.LongRef longRef;
        Object obj2;
        Object result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        int i2 = 2;
        long j = 0;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            flowCollector = (FlowCollector) this.L$1;
            flowKt__DelayKt$debounceInternal$1 = this;
            produce$default = ProduceKt.produce$default(coroutineScope, null, 0, new FlowKt__DelayKt$debounceInternal$1$values$1(this.$this_debounceInternal, null), 3, null);
            objectRef = new Ref.ObjectRef();
            if (objectRef.element != NullSurrogateKt.DONE) {
            }
        } else if (i == 1) {
            Ref.LongRef longRef2 = (Ref.LongRef) this.L$3;
            objectRef = (Ref.ObjectRef) this.L$2;
            produce$default = (ReceiveChannel) this.L$1;
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            longRef = longRef2;
            flowKt__DelayKt$debounceInternal$1 = this;
            objectRef.element = null;
            FlowKt__DelayKt$debounceInternal$1<T> flowKt__DelayKt$debounceInternal$12 = flowKt__DelayKt$debounceInternal$1;
            obj2 = coroutine_suspended;
            Ref.LongRef longRef3 = longRef;
            FlowCollector flowCollector2 = flowCollector;
            ReceiveChannel receiveChannel = produce$default;
            if (DebugKt.getASSERTIONS_ENABLED()) {
            }
            flowKt__DelayKt$debounceInternal$12.L$0 = flowCollector2;
            flowKt__DelayKt$debounceInternal$12.L$1 = receiveChannel;
            flowKt__DelayKt$debounceInternal$12.L$2 = objectRef;
            flowKt__DelayKt$debounceInternal$12.L$3 = longRef3;
            flowKt__DelayKt$debounceInternal$12.label = i2;
            FlowKt__DelayKt$debounceInternal$1<T> flowKt__DelayKt$debounceInternal$13 = flowKt__DelayKt$debounceInternal$12;
            SelectInstance selectInstance = new SelectInstance(flowKt__DelayKt$debounceInternal$13);
            SelectInstance selectInstance2 = selectInstance;
            if (objectRef.element != null) {
            }
            selectInstance2.invoke(receiveChannel.getOnReceiveCatching(), new FlowKt__DelayKt$debounceInternal$1$3$2(objectRef, flowCollector2, null));
            result = selectInstance.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            }
            if (result != obj2) {
            }
        } else if (i == 2) {
            Ref.ObjectRef objectRef2 = (Ref.ObjectRef) this.L$2;
            ReceiveChannel receiveChannel2 = (ReceiveChannel) this.L$1;
            FlowCollector flowCollector3 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            flowCollector = flowCollector3;
            produce$default = receiveChannel2;
            objectRef = objectRef2;
            flowKt__DelayKt$debounceInternal$1 = this;
            i2 = 2;
            j = 0;
            if (objectRef.element != NullSurrogateKt.DONE) {
                longRef = new Ref.LongRef();
                if (objectRef.element != null) {
                    Function1<T, Long> function1 = flowKt__DelayKt$debounceInternal$1.$timeoutMillisSelector;
                    Symbol symbol = NullSurrogateKt.NULL;
                    T t = objectRef.element;
                    if (t == symbol) {
                        t = null;
                    }
                    longRef.element = function1.invoke(t).longValue();
                    if (!(longRef.element >= j)) {
                        throw new IllegalArgumentException("Debounce timeout should not be negative".toString());
                    }
                    if (longRef.element == j) {
                        Symbol symbol2 = NullSurrogateKt.NULL;
                        T t2 = objectRef.element;
                        if (t2 == symbol2) {
                            t2 = null;
                        }
                        flowKt__DelayKt$debounceInternal$1.L$0 = flowCollector;
                        flowKt__DelayKt$debounceInternal$1.L$1 = produce$default;
                        flowKt__DelayKt$debounceInternal$1.L$2 = objectRef;
                        flowKt__DelayKt$debounceInternal$1.L$3 = longRef;
                        flowKt__DelayKt$debounceInternal$1.label = 1;
                        if (flowCollector.emit(t2, flowKt__DelayKt$debounceInternal$1) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        objectRef.element = null;
                    }
                }
                FlowKt__DelayKt$debounceInternal$1<T> flowKt__DelayKt$debounceInternal$122 = flowKt__DelayKt$debounceInternal$1;
                obj2 = coroutine_suspended;
                Ref.LongRef longRef32 = longRef;
                FlowCollector flowCollector22 = flowCollector;
                ReceiveChannel receiveChannel3 = produce$default;
                if (DebugKt.getASSERTIONS_ENABLED()) {
                    if (!(objectRef.element == null || longRef32.element > j)) {
                        throw new AssertionError();
                    }
                }
                flowKt__DelayKt$debounceInternal$122.L$0 = flowCollector22;
                flowKt__DelayKt$debounceInternal$122.L$1 = receiveChannel3;
                flowKt__DelayKt$debounceInternal$122.L$2 = objectRef;
                flowKt__DelayKt$debounceInternal$122.L$3 = longRef32;
                flowKt__DelayKt$debounceInternal$122.label = i2;
                FlowKt__DelayKt$debounceInternal$1<T> flowKt__DelayKt$debounceInternal$132 = flowKt__DelayKt$debounceInternal$122;
                SelectInstance selectInstance3 = new SelectInstance(flowKt__DelayKt$debounceInternal$132);
                SelectInstance selectInstance22 = selectInstance3;
                if (objectRef.element != null) {
                    selectInstance22.onTimeout(longRef32.element, new FlowKt__DelayKt$debounceInternal$1$3$1(flowCollector22, objectRef, null));
                }
                selectInstance22.invoke(receiveChannel3.getOnReceiveCatching(), new FlowKt__DelayKt$debounceInternal$1$3$2(objectRef, flowCollector22, null));
                result = selectInstance3.getResult();
                if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    DebugProbesKt.probeCoroutineSuspended(flowKt__DelayKt$debounceInternal$132);
                }
                if (result != obj2) {
                    return obj2;
                }
                coroutine_suspended = obj2;
                flowKt__DelayKt$debounceInternal$1 = flowKt__DelayKt$debounceInternal$122;
                produce$default = receiveChannel3;
                flowCollector = flowCollector22;
                i2 = 2;
                j = 0;
                if (objectRef.element != NullSurrogateKt.DONE) {
                    return Unit.INSTANCE;
                }
            }
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
