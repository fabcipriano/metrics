def profile = null;
def out = new File("gambis.txt");
def file = new File("carga.csv");

println "save to ${out}"

out.withWriter { writer ->

    file.splitEachLine(",") {fields ->
        def CODCTG= fields[0]
        def NOMCTG= fields[1]
        def DATCADCTG= fields[2]
        def ENDARQCSS= fields[3]
        def FLGFULL= fields[4]
        def CODEMP=fields[5]
        def SECPROJMINAS= fields[6]
        def USASECMINAS= fields[7]
        def PROJMINAS= fields[8]                            

        writer.writeLine("profiles.put(${CODCTG},new PortalCadctg(${CODCTG}, \"${NOMCTG}\", new Date(\"${DATCADCTG}\"), \"${ENDARQCSS}\", \"${FLGFULL}\", \"${CODEMP}\"));")				        
    }
}

println "YYYYYEEEEEEEEEEEEEEEEESSSSSsssssssssssssssssssssssss!!!!!!!"

