import re

from bs4 import BeautifulSoup

from VacsHrefs import get_html, href_getter


def parse(html):
    soup = BeautifulSoup(get_html(html), "html.parser")
    div = soup.find_all("div", class_="b-vacancy-desc-wrapper")
    return div

def text_DICT(text):
    text = str(text)
    result = dict()
    words = text.split()
    for i in words:
        i = re.match(r'[\w-]+', i)
        try:
            i = i.group(0)
        except:
            continue
        if len(i) < 3 or i[0].islower() or i.isdigit():
            continue
        elif i in result:
            result[i] += 1
        else:
            result[i] = 1
    return result

def dicts_merger(dict1, dict2):
    for i in dict2:
        if i in dict1:
            dict1[i] += dict2[i]
        else:
            dict1[i] = dict2[i]
    return dict1

def getDict(vacsHrefs):
    resultDict = dict()
    counter = 1
    vacsLen = len(vacsHrefs)
    for i in vacsHrefs:
        textSet = parse(i)
        print(str(counter*100/vacsLen)[:5], end="% \t\t--- ")
        counter+=1
        for j in textSet:
            print("parsing: " + i)
            resultDict = dicts_merger(resultDict, text_DICT(j))
    return resultDict