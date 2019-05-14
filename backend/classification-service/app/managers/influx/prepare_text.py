import re

from nltk.stem import WordNetLemmatizer
from nltk.corpus import stopwords


class TextPrettifier:
    """
    Очищение текста перед началом формирования модели
    """

    @staticmethod
    def get_prepared_text(text: str) -> str:

        stemmer = WordNetLemmatizer()

        stop_words = stopwords.words('russian')
        stop_words.extend(['что', 'это', 'так', 'вот', 'быть', 'как', 'в', '—', 'к', 'на'])

        document = re.sub(r'\W', ' ', text)
        document = re.sub(r'\s+[a-zA-Z]\s+', ' ', document)
        document = re.sub(r'\^[a-zA-Z]\s+', ' ', document)
        document = re.sub(r'\s+', ' ', document, flags=re.I)
        document = re.sub(r'^b\s+', '', document)
        document = document.lower()
        document = document.split()

        document = [word for word in document if (word not in stop_words)]
        document = [stemmer.lemmatize(word) for word in document]
        document = ' '.join(document)

        return document
