def write(resDict, path, prop):
    prof = prop["vacancy"].replace("+", "_")
    with open(path + "/" + prof +".txt", 'w') as outfile:
        outfile.writelines(prof + "\n")
        for i in resDict:
            outfile.writelines(i + " : " +str(resDict[i]) + "\n")