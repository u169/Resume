import re
import urllib.request

from bs4 import BeautifulSoup


def get_html(url):
    response = urllib.request.urlopen(url)
    return response.read()


def href_getter(soup_list):
    result = list()
    while len(soup_list) != 0:
        href = str(soup_list.pop())
        href = re.findall(r'(\bhttps://.+)\?.+target', href)
        result.extend(href)
    return result


def parse(html):
    soup = BeautifulSoup(get_html(html), "html.parser")
    div = soup.find_all("div", class_="search-result-item__head")
    return href_getter(div)


def getVacshHref(prop):
    vacsHrefs = list()
    value = int(prop["value"])
    pages = value // 20
    if  value % 20 > 0:
        pages+=1
    print(str(pages) + "...")
    for i in range(pages):
        url = prop["templ"] + "&text=" + prop["vacancy"] + "&page=" + str(i)
        print(str(i+1)+ "'nd page: ", end = "")
        x = parse(url)
        print(url + ": " + str(len(x)) + " vacs")
        vacsHrefs.extend(x)
        if len(x) < 20:
            break
    print()
    return vacsHrefs
