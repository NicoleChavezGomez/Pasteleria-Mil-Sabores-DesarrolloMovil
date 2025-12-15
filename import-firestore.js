/**
 * Script para importar datos a Firebase Firestore
 * 
 * Requisitos:
 * 1. Node.js instalado
 * 2. Ejecutar: npm install firebase-admin
 * 3. Obtener credenciales de Firebase (service account key)
 * 
 * Uso:
 * node import-firestore.js
 */

const admin = require('firebase-admin');
const fs = require('fs');
const path = require('path');

// IMPORTANTE: Reemplaza esto con la ruta a tu archivo de credenciales de Firebase
// O usa las variables de entorno
const serviceAccount = require('./firebase-service-account-key.json');

// Inicializar Firebase Admin
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

// Función para importar categorías
async function importCategories() {
  console.log('📦 Importando categorías...');
  
  const categoriesData = JSON.parse(
    fs.readFileSync(path.join(__dirname, 'firestore_import_categories.json'), 'utf8')
  );
  
  const batch = db.batch();
  let count = 0;
  
  for (const [docId, data] of Object.entries(categoriesData)) {
    const docRef = db.collection('categories').doc(docId);
    batch.set(docRef, data);
    count++;
  }
  
  await batch.commit();
  console.log(`✅ ${count} categorías importadas exitosamente`);
}

// Función para importar productos
async function importProducts() {
  console.log('📦 Importando productos...');
  
  const productsData = JSON.parse(
    fs.readFileSync(path.join(__dirname, 'firestore_import_products.json'), 'utf8')
  );
  
  // Firestore tiene límite de 500 operaciones por batch
  // Dividimos en batches si es necesario
  const batchSize = 500;
  const entries = Object.entries(productsData);
  
  for (let i = 0; i < entries.length; i += batchSize) {
    const batch = db.batch();
    const chunk = entries.slice(i, i + batchSize);
    
    for (const [docId, data] of chunk) {
      const docRef = db.collection('products').doc(docId);
      batch.set(docRef, data);
    }
    
    await batch.commit();
    console.log(`✅ Lote ${Math.floor(i / batchSize) + 1} procesado (${chunk.length} productos)`);
  }
  
  console.log(`✅ ${entries.length} productos importados exitosamente`);
}

// Función principal
async function main() {
  try {
    console.log('🚀 Iniciando importación a Firestore...\n');
    
    await importCategories();
    console.log('');
    await importProducts();
    
    console.log('\n✨ Importación completada exitosamente!');
    process.exit(0);
  } catch (error) {
    console.error('❌ Error durante la importación:', error);
    process.exit(1);
  }
}

main();

