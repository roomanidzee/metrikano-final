from app import app

app.run(host=app.config.host, port=app.config.port,
        debug=app.config.debug, use_reloader=app.config.use_reloader)
