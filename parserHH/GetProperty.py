def getProperty(path):
    with open(path, 'r') as outfile:
        templ = outfile.readline()
        templ = templ.strip().replace("template=", "").strip()
        print(templ)
        vacancy = outfile.readline()
        vacancy = vacancy.strip().replace("vacancy=", "").replace(" ", "+")
        print(vacancy)
        outfile.readline()
        value = outfile.readline()
        value = value.strip().replace("value=", "")
        print(value)
        periodicity = outfile.readline()
        periodicity = periodicity.strip().replace("periodicity=", "")
        print(value)
        return {'templ' : templ, "vacancy" : vacancy, "value" : value, "periodicity" : periodicity}