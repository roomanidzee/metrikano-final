FROM python:3.7.2-alpine
MAINTAINER Andrey Romanov <steampart@gmail.com>

ARG SETTINGS_MODULE_FOR_DYNACONF
ARG FLASK_ENV

WORKDIR /app
COPY . .
EXPOSE 5001

RUN echo "http://dl-cdn.alpinelinux.org/alpine/latest-stable/main" > /etc/apk/repositories
RUN echo "http://dl-cdn.alpinelinux.org/alpine/latest-stable/community" >> /etc/apk/repositories
RUN apk --no-cache --update-cache add git gcc gfortran build-base wget freetype-dev libpng-dev openblas-dev
RUN ln -s /usr/include/locale.h /usr/include/xlocale.h
RUN pip install numpy
RUN pip install scipy
RUN pip install pandas
RUN pip install -r requirements.txt
RUN python -c "import nltk; nltk.download('stopwords'); nltk.download('wordnet'); nltk.download('sentiwordnet')"

CMD ["python", "run.py"]
