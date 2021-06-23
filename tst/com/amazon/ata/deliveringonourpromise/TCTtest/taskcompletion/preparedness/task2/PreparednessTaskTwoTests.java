package com.amazon.ata.deliveringonourpromise.TCTtest.taskcompletion.preparedness.task2;

import com.amazon.ata.test.helper.AtaTestHelper;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

public class PreparednessTaskTwoTests {
    @Test
    public void preparednessTaskTwo_preparednessTaskTwoFile_existsAndItIsNotEmpty() {
        // GIVEN
        String taskTwoFileName = "preparedness-task2.txt";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(taskTwoFileName);

        // THEN
        assertFalse(content.isEmpty());
    }
}
