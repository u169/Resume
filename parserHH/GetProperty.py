def getProperty(path):
    try:
        with open(path, 'r') as outfile:
            outfile.readline()
            outfile.readline()
            outfile.readline()
            outfile.readline()
            templ = outfile.readline()
            templ = templ.strip().replace("template=", "").strip()
            vacancy = outfile.readline()
            vacancy = vacancy.strip().replace("vacancy=", "").replace(" ", "+")
            value = outfile.readline()
            value = value.strip().replace("value=", "")
            periodicity = outfile.readline()
            periodicity = periodicity.strip().replace("periodicity=", "")
            print("property is right")
            return {'templ' : templ, "vacancy" : vacancy, "value" : value, "periodicity" : periodicity}
    except:
        print("Wrong '/cfg/config.properties' file")