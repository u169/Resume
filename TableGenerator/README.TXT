Программа получает на вход 2 файла (table-params.xml, data.tsv) и выдаёт таблицу *.txt


Подробное описание ищите "resources/TZ/Report_generator.txt"


1. Поместите файлы
        *.xml (шаблон смотри "resources/TZ/settings.xml")
        *.tsv (пример смотри "resources/TZ/source-data.tsv")
    в директорию resources

2. Запустите Main.class с аргументами
    Пример параметров: settings.xml source-data.tsv example-report.txt

3. Найти результат в "NewTablesThere/example-report.txt"

Примечания:

- Имена файлов могут быть любыми, главное введите их же как аргументы запуска.
- Шаблон .xml лучше скопировать и заменить значения. Разрешено добавлять доп колонки ("column") и
    изменять значения "height" (высота листа таблицы) и "width" (ширина соответсвенно)
