import { goalData } from './data.js';
import express from 'express';

const app = express()
const port = 3000


app.get('/', (req, res) => {
  res.send('Running app!')
})

app.get('/id/:id/name/:name/discovery', (req, res) => {
  const { name, id } = req.params;

  console.log(name)
  console.log(id)

  const list = goalData[id] && goalData[id][name];

  if (!list) {
    return res.status(404).send({ 
      error: "Meta no encontrada" 
    });
  }

  res.send({
    name: name,
    id: id,
    
  });
});

app.listen(port, () => {
  console.log(`App listening on port ${port}`)
})