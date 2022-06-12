var express = require('express');
var bodyParser = require('body-parser');

var usersRoutes = require('./routes/users.js');

const app = express();
let PORT = process.env.PORT || 3000;

app.use(bodyParser.json());

app.use('/', usersRoutes);

app.get("/", (req, res) => res.send("Welcome to the Users API!"));
app.all("*", (req, res) =>res.send("You've tried reaching a route that doesn't exist."));

app.listen(PORT, () =>console.log(`Server running on port: http://localhost:${PORT}`));