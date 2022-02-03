package com.amazon.ata.deliveringonourpromise.TCTtest.taskcompletion.preparedness.task3;

import com.amazon.ata.test.helper.AtaTestHelper;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

public class PreparednessTaskThreeTests {
    @Test
    public void preparednessTaskThree_preparednessTaskThreeFile_existsAndItIsNotEmpty() {
        // GIVEN
        String taskThreeFileName = "preparedness-task3.txt";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(taskThreeFileName);

        // THEN
        assertFalse(content.isEmpty());
    }
}
