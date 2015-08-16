package com.benjiweber;

import com.benjiweber.typeref.DefaultValue;
import com.benjiweber.typeref.MethodAwareConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class DefaultValuesAndMethodReferences {

    @Mock UsesPrimitives mock;

    public interface UsesPrimitives {
        void takesInt(int i);
        void takesLong(long l);
        void takesShort(short s);
        void takesBoolean(boolean b);
        void takesChar(char c);
        void takesFloat(float f);
        void takesDouble(double d);
    }

    @Test(expected = NullPointerException.class)
    public void passing_null_throws_npe() {
        MethodAwareConsumer<Integer> f = mock::takesInt;
        f.accept(null);
    }

    @Test
    public void int_param() {
        check(mock::takesInt, () -> verify(mock).takesInt(0));
    }

    @Test
    public void longParam() {
        check(mock::takesLong, () -> verify(mock).takesLong(0L));
    }

    @Test
    public void shortParam() {
        check(mock::takesShort, () -> verify(mock).takesShort((short)0.0));
    }

    @Test
    public void booleanParam() {
        check(mock::takesBoolean, () -> verify(mock).takesBoolean(false));
    }

    @Test
    public void charParam() {
        check(mock::takesChar, () -> verify(mock).takesChar(' '));
    }

    @Test
    public void floatParam() {
        check(mock::takesFloat, () -> verify(mock).takesFloat(0.0f));
    }

    @Test
    public void doubleParam() {
        check(mock::takesDouble, () -> verify(mock).takesDouble(0.0));
    }

    private <T> void check(MethodAwareConsumer<T> consumer, Runnable verification) {
        consumer.accept(DefaultValue.ofType(consumer.parameter(0).getType()));
        verification.run();
        verifyNoMoreInteractions(mock);
    }
}
