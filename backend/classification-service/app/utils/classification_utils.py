import re


class ClassificationUtils:
    """
    Класс утилит для классификации
    """

    @staticmethod
    def clear_file_string(string_to_clean: str) -> str:
        """
        Метод для очищения строки от байтов из файла
        :param string_to_clean: строка для очищения
        :return: преобразованная строка
        """

        temp_str = str(string_to_clean)
        temp_str = temp_str.replace("b", "")
        temp_str = temp_str.replace("\\", "")
        temp_str = temp_str.replace("\'", "")
        temp_str = temp_str.replace("n", "")

        return temp_str

    @staticmethod
    def string_to_number(string_to_transform: str):
        """
        Метод для преобразования строки с символами в число
        :param string_to_transform: строка для преобразования
        :return: число
        """

        result = 0

        for char_item in string_to_transform:
            result += ord(char_item)

        return result

    @staticmethod
    def clear_post_text(dirty_text: str):
        """
        Метод для очищения текста записи из ВК от смайликов и посторонних символов
        :param dirty_text: текст для очищения
        :return: преобразованный текст
        """

        text_pattern = \
            re.compile(r"^[ ?.!/;:\""
                       r"\U0001F600-\U0001F64F "
                       r"\U0001F300-\U0001F5FF \U0001F680-\U0001F6FF \U0001F1E0-\U0001F1FF]$",
                       flags=re.UNICODE)

        return re.sub(text_pattern, ' ', str(dirty_text.encode('utf-8').decode('utf-8')))
