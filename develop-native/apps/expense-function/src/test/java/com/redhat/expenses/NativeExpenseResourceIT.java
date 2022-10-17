package com.redhat.expenses;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeExpenseResourceIT extends ExpenseResourceTest {

    // Execute the same tests but in native mode.
}