package com.amazon.ata.deliveringonourpromise.TCTtest.taskcompletion.preparedness.task4;

import com.amazon.ata.test.helper.AtaTestHelper;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramIncludesContainsRelationship;
import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramIncludesImplementsRelationship;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class PreparednessTaskFourTests {
    private static final String DIAGRAM_FILENAME = "deliveringonourpromise_CD.puml";

    @Test(dataProvider = "requiredClasses")
    public void preparednessTaskFour_deliveringOnOurPromiseClassDiagram_containsRequiredClasses(String classPattern) {
        // GIVEN - class name to look for
        // WHEN
        String plantUmlContent = getClassDiagramContent();

        // THEN
        assertFalse(plantUmlContent.isEmpty());

        Pattern p = Pattern.compile("\\b" + classPattern + "\\b");
        Matcher m = p.matcher(plantUmlContent);
        assertTrue(m.find(), String.format("Expected %s to contain the class: %s", DIAGRAM_FILENAME, classPattern));
    }

    @DataProvider(name = "requiredClasses")
    public static Object[][] getRequiredClasses() {
        return new Object[][] {
            {"GetPromiseHistoryByOrderIdActivity"},
            {"OrderDao"},
            {"PromiseDao"},
            {"PromiseHistoryClient"},
            {"OrderManipulationAuthorityClient"},
            {"DeliveryPromiseServiceClient"},
            {"App"},
            {"Order"},
            {"OrderItem"},
            {"Promise"},
            {"PromiseHistory"}
        };
    }


    @Test(dataProvider = "requiredInterfaces")
    public void prepaornnessTaskFour_deliveringOnOurPromiseClassDiagram_containsRequiredInterfaces(
            String interfacePattern) {

        // GIVEN - class name to look for
        // WHEN
        String plantUmlContent = getClassDiagramContent();

        // THEN
        assertFalse(plantUmlContent.isEmpty());

        Pattern p = Pattern.compile("\\b" + interfacePattern + "\\b");
        Matcher m = p.matcher(plantUmlContent);
        assertTrue(m.find(),
                   String.format("Expected %s to contain the interface: %s", DIAGRAM_FILENAME, interfacePattern)
        );
    }

    @DataProvider(name = "requiredInterfaces")
    public static Object[][] getRequiredInterfaces() {
        return new Object[][] {{"ReadOnlyDao"}};
    }


    @Test(dataProvider = "requiredContainsRelationships")
    public void preparednessTaskFour_deliveringOnOurPromiseClassDiagram_containsRequiredContainsRelationships(
            String containingType,
            String containedType) {

        // GIVEN
        // WHEN
        String plantUmlContent = getClassDiagramContent();

        // THEN
        assertClassDiagramIncludesContainsRelationship(plantUmlContent, containingType, containedType);
    }

    @DataProvider(name = "requiredContainsRelationships")
    public static Object[][] getRequiredContainsRelationships() {
        return new Object[][] {
            {"PromiseHistoryClient", "GetPromiseHistoryByOrderIdActivity"},
            {"GetPromiseHistoryByOrderIdActivity", "PromiseDao"},
            {"GetPromiseHistoryByOrderIdActivity", "OrderDao"},
            {"PromiseDao", "DeliveryPromiseServiceClient"},
            {"PromiseDao", "OrderManipulationAuthorityClient"},
            {"OrderDao", "OrderManipulationAuthorityClient"},
            {"PromiseHistory", "Order"},
            {"PromiseHistory", "Promise"},
            {"Order", "OrderItem"}
        };
    }


    @Test(dataProvider = "requiredImplementsRelationships")
    public void preparednessTaskFour_deliveringOnOurPromiseClassDiagram_containsRequiredImplementsRelationships(
            String implementingClass,
            String implementedInterface) {

        // GIVEN
        // WHEN
        String plantUmlContent = getClassDiagramContent();

        // THEN
        assertClassDiagramIncludesImplementsRelationship(plantUmlContent, implementingClass, implementedInterface);
    }

    @DataProvider(name = "requiredImplementsRelationships")
    public Object[][] getRequiredImplementsRelationships() {
        return new Object[][] {
            {"OrderDao", "ReadOnlyDao"},
            {"PromiseDao", "ReadOnlyDao"}
        };
    }

    private String getClassDiagramContent() {
        return AtaTestHelper.getFileContentFromResources(DIAGRAM_FILENAME);
    }

}
