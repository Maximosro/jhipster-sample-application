package com.mycompany.sanity;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.mycompany.sanity");

        noClasses()
            .that()
            .resideInAnyPackage("com.mycompany.sanity.service..")
            .or()
            .resideInAnyPackage("com.mycompany.sanity.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.mycompany.sanity.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
