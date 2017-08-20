from collections import OrderedDict

import GetProperty
import GetWords
import VacsHrefs
import WriteInFile


def main():
    prop = GetProperty.getProperty("cfg/config.properties")
    vacancyPages = VacsHrefs.getVacshHref(prop)
    print("vacancies hrefs was gotten : " + str(len(vacancyPages)) + " vacs")
    print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    allDict = GetWords.getDict(vacancyPages)
    result = dict()
    for i in allDict:
        count = allDict[i]
        if count > int(prop["periodicity"]):
            result[i] = count
    sorted_x = OrderedDict(sorted(result.items(), key=lambda x: x[1], reverse=True))
    print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    print("parsing was ended!")
    WriteInFile.write(sorted_x, "out", prop)
    print("pls, open '/out/" + prop["vacancy"].replace("+", "_") + ".txt' for view information")


if __name__ == '__main__':
    main()