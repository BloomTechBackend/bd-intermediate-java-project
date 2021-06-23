package com.amazon.ata.deliveringonourpromise.TCTtest.wrapper;

import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.test.reflect.ClassQuery;
import com.amazon.ata.test.reflect.MethodQuery;
import com.amazon.ata.test.wrapper.WrapperBase;

import com.google.common.collect.ImmutableList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.amazon.ata.test.helper.AtaTestHelper.failTestWithException;

/**
 * Wraps the PromiseAsinComparator class, that participants will
 * create from scratch.
 */
public class PromiseAsinComparatorWrapper extends WrapperBase {
    /**
     * Saves the wrapped class instance and type, ensuring the wrapped
     * instance looks like the right type for the given wrapper class.
     *
     * @param wrappedInstance the instance to be wrapped
     */
    public PromiseAsinComparatorWrapper(Object wrappedInstance) {
        super(wrappedInstance);
    }

    /**
     * Creates a new PromiseAsinComparatorWrapper instance, providing its own
     * comparator instance to wrap (if possible).
     */
    public PromiseAsinComparatorWrapper() {
        super(constructComparator());
    }

    @Override
    public Class<?> getWrappedClass() {
        return PromiseAsinComparatorWrapper.findPromiseAsinComparatorWrapperClass();
    }

    /**
     * Invokes the compare() method if it exists, and returns its result. Otherwise,
     * will fail assertion.
     */
    public int compare(Promise promise1, Promise promise2) {
        Method compareMethod = MethodQuery.inType(getWrappedClass())
                                   .withExactName("compare")
                                   .withExactArgTypes(ImmutableList.of(Promise.class, Promise.class))
                                   .withReturnType(int.class)
                                   .findMethodOrFail();

        return (int) invokeInstanceMethodWithReturnValue(compareMethod, promise1, promise2);
    }

    /**
     * Find the wrapped class, if it exists. If class not found, will
     * fail()
     * @return the PromiseAsinComparator class if it exists
     */
    public static Class<?> findPromiseAsinComparatorWrapperClass() {
        return ClassQuery.inExactPackage("com.amazon.ata.deliveringonourpromise.comparators")
                   .withExactSimpleName("PromiseAsinComparator")
                   .findClassOrFail();
    }

    private static Object constructComparator() {
        Class<?> wrappedClass = findPromiseAsinComparatorWrapperClass();
        Constructor<?> comparatorConstructor = WrapperBase.getConstructor(wrappedClass);

        Object newInstance = null;
        try {
            newInstance = comparatorConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            failTestWithException(e, "Could not instantiate a PromiseAsinComparator");
        }

        return newInstance;
    }
}
