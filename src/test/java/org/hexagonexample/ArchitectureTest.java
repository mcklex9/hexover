package org.hexagonexample;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "org.hexagonexample",
        importOptions = {ImportOption.DoNotIncludeTests.class})
public class ArchitectureTest {
    private static final String DOMAIN_LAYER = "Domain";

    private static final String DOMAIN_LAYER_PACKAGE = "org.hexagonexample.domain..";

    private static final String APPLICATION_LAYER = "Application";

    private static final String APPLICATION_LAYER_PACKAGE = "org.hexagonexample.application..";

    private static final String ADAPTERS_LAYER = "Adapters";

    private static final String ADAPTERS_LAYER_PACKAGE = "org.hexagonexample.adapters..";

    @ArchTest
    static final ArchRule layersShouldRespectOnionArchitecture = layeredArchitecture().consideringAllDependencies()
            .layer(DOMAIN_LAYER).definedBy(DOMAIN_LAYER_PACKAGE)
            .layer(APPLICATION_LAYER).definedBy(APPLICATION_LAYER_PACKAGE)
            .layer(ADAPTERS_LAYER).definedBy(ADAPTERS_LAYER_PACKAGE)
            .whereLayer(ADAPTERS_LAYER).mayNotBeAccessedByAnyLayer()
            .whereLayer(APPLICATION_LAYER).mayOnlyBeAccessedByLayers(ADAPTERS_LAYER)
            .whereLayer(DOMAIN_LAYER).mayOnlyBeAccessedByLayers(ADAPTERS_LAYER, APPLICATION_LAYER);
}
