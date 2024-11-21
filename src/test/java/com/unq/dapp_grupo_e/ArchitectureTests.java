package com.unq.dapp_grupo_e;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;

@ActiveProfiles("test") 
@SpringBootTest
public class ArchitectureTests {

    @Test
    void archAboutPackages() {

        ArchRule rule = layeredArchitecture()
            .consideringAllDependencies()
            .layer("Controller").definedBy("..controller..")
            .layer("Service").definedBy("..service..")
            .layer("Repository").definedBy("..repository")
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
            .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service");

        JavaClasses classes = new ClassFileImporter().importPackages("com.unq.dapp_grupo_e.service", 
                                                                                 "com.unq.dapp_grupo_e.repository",
                                                                                 "com.unq.dapp_grupo_e.controller");

        rule.check(classes);
    }
    
    @Test
    void cyclesCheck() {
        ArchRule rule = SlicesRuleDefinition.slices()
                .matching("com.unq.dapp_grupo_e.(*)..")
                .should()
                .beFreeOfCycles();
        JavaClasses projectClasses = new ClassFileImporter().importPackages("com.unq.dapp_grupo_e");

        rule.check(projectClasses);
    }
}
