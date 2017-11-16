const fetch = require('node-fetch');

async function students() {
    const response = await fetch('http://localhost:8080/students/3');
    const json = await response.json();
    console.log(`
        Status: ${JSON.stringify(response.status)}
        Headers: ${JSON.stringify(response.headers)}
        Body: ${JSON.stringify(json)}
    `)
}

students();
