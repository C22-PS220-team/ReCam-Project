var express = require('express');

var kertasjson = require("./data.json"); 
var cardboardjson = require("./data2.json"); 
var plastikjson = require("./data3.json"); 
var besijson = require("./data4.json"); 
var kacajson = require("./data5.json"); 
var testjson = require("./test.json"); 



const router = express.Router();

router.get('/cardboard', (req, res) =>
{
    res.json(cardboardjson.cardboard);
} );

router.get('/plastik', (req, res) =>
{
    res.json(plastikjson.plastik);
} );

router.get('/besi', (req, res) =>
{
    res.json(besijson.besi);
} );

router.get('/kaca', (req, res) =>
{
    res.json(kacajson.kaca);
} );

router.get('/kertas', (req, res) =>
{
    res.json(kertasjson.kertas);
} );







module.exports = router;
