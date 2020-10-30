package dev.randykinne.me

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.jupiter.api.Test

class ArchTest {

    @Test
    fun servicesAndRepositoriesShouldNotDependOnWebLayer() {

        val importedClasses = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("dev.randykinne.me")

        noClasses()
            .that()
                .resideInAnyPackage("dev.randykinne.me.service..")
            .or()
                .resideInAnyPackage("dev.randykinne.me.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..dev.randykinne.me.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses)
    }
}
