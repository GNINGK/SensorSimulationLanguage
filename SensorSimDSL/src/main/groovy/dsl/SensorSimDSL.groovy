package main.groovy.dsl

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import static org.codehaus.groovy.syntax.Types.*

class SensorSimDSL {
    private GroovyShell shell
    private CompilerConfiguration configuration
    private SensorSimBinding binding
    private SensorSimBaseScript baseScript


    SensorSimDSL() {
        binding = new SensorSimBinding()
        binding.setSensorSimModel(new SensorSimModel(binding))
        configuration = getDSLConfiguration()
        configuration.setScriptBaseClass("main.groovy.dsl.SensorSimBaseScript")
        shell = new GroovyShell(configuration)
    }

    private static CompilerConfiguration getDSLConfiguration() {
        def secure = new SecureASTCustomizer()
        secure.with {
            //disallow closure creation
            closuresAllowed = true
            //disallow method definitions
            methodDefinitionAllowed = true
            //empty white list => forbid imports
            importsWhitelist = [
                    'java.lang.*'
            ]
            staticImportsWhitelist = []
            staticStarImportsWhitelist= []
            //language tokens disallowed
//			tokensBlacklist= []
            //language tokens allowed
            tokensWhitelist= [
                DIVIDE
            ]
            //types allowed to be used  (including primitive types)
            constantTypesClassesWhiteList= [
                    int, Integer, BigDecimal, Number, Integer.TYPE, String, Object
            ]
            //classes who are allowed to be receivers of method calls
            receiversClassesWhiteList= [
                    int, BigDecimal, Number, Integer, String, Object
            ]
        }

        def configuration = new CompilerConfiguration()
        configuration.addCompilationCustomizers(secure)

        return configuration
    }

    void eval(File scriptFile) {
        Script script = shell.parse(scriptFile)

        binding.setScript(script)
        script.setBinding(binding)

        script.run()
    }
}