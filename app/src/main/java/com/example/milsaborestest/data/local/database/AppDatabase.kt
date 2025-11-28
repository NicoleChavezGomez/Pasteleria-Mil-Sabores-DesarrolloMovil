package com.example.milsaborestest.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        CartEntity::class,
        UserEntity::class,
        CategoryEntity::class,
        ProductEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    
    companion object {
        private var database: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "milsabores_database"
                )
                .fallbackToDestructiveMigration() // Eliminar la base de datos al cambiar la versión
                .build()
                
                // Insertar datos por defecto después de construir la base de datos
                CoroutineScope(Dispatchers.IO).launch {
                    insertarDatosPorDefecto(database!!, context.applicationContext)
                }
            }
            return database!!
        }
        
        private suspend fun insertarDatosPorDefecto(db: AppDatabase, context: Context) {
            val userDao = db.userDao()
            val categoryDao = db.categoryDao()
            val productDao = db.productDao()
            
            // Verificar si ya existen usuarios para no duplicar
            val usuariosExistentes = userDao.obtenerTodos()
            if (usuariosExistentes.isEmpty()) {
                val usuarios = listOf(
                    UserEntity(
                        nombre = "Admin Mil Sabores",
                        email = "admin@milsabores.com",
                        contrasena = "123456"
                    ),
                    UserEntity(
                        nombre = "Cliente Demo",
                        email = "cliente@milsabores.com",
                        contrasena = "123456"
                    ),
                    UserEntity(
                        nombre = "Usuario Test",
                        email = "test@milsabores.com",
                        contrasena = "123456"
                    )
                )
                
                usuarios.forEach { userDao.insertar(it) }
            }
            
            // Verificar si ya existen categorías para no duplicar
            val categoriasExistentes = categoryDao.contar()
            if (categoriasExistentes == 0) {
                // Insertar categorías por defecto
                val categorias = listOf(
                    CategoryEntity(id = "tortas-cuadradas", nombre = "Tortas Cuadradas", icono = "square"),
                    CategoryEntity(id = "tortas-circulares", nombre = "Tortas Circulares", icono = "circle"),
                    CategoryEntity(id = "postres-individuales", nombre = "Postres Individuales", icono = "cookie"),
                    CategoryEntity(id = "productos-sin-azucar", nombre = "Productos Sin Azúcar", icono = "favorite"),
                    CategoryEntity(id = "pasteleria-tradicional", nombre = "Pastelería Tradicional", icono = "home"),
                    CategoryEntity(id = "productos-sin-gluten", nombre = "Productos Sin Gluten", icono = "eco"),
                    CategoryEntity(id = "productos-veganos", nombre = "Productos Veganos", icono = "park"),
                    CategoryEntity(id = "tortas-especiales", nombre = "Tortas Especiales", icono = "star")
                )
                categoryDao.insertarTodas(categorias)
                
                // Insertar productos por defecto
                val productos = listOf(
                    // Tortas Cuadradas
                    ProductEntity(
                        id = "TC001", nombre = "Torta Cuadrada de Chocolate", precio = 45990,
                        imagen = "https://delicakesysnacks.com/wp-content/uploads/2025/01/vitxekmdoeio3sgmh5dr-1.webp",
                        descripcion = "Deliciosa torta de chocolate con relleno de crema.",
                        descripcionDetallada = "Exquisita torta de chocolate premium elaborada con los mejores ingredientes. Capas de bizcocho de chocolate esponjoso, relleno de crema de chocolate belga y decoración artesanal con virutas de chocolate. Perfecta para celebraciones especiales, cumpleaños y eventos importantes. Cada bocado es una experiencia de sabor inolvidable.",
                        rating = 4.8, reviews = 24, porciones = "10-15 personas", calorias = "350 cal/porción",
                        ingredientes = "Chocolate premium, harina, huevos, azúcar, mantequilla, crema de leche, cacao en polvo",
                        categoryId = "tortas-cuadradas"
                    ),
                    ProductEntity(
                        id = "TC002", nombre = "Torta Cuadrada de Frutas", precio = 22990,
                        imagen = "https://thumbs.dreamstime.com/b/este-delicioso-pastel-de-fruta-cuadrada-con-capas-esponja-ligera-y-crema-delicada-adornado-generosidad-est%C3%A1-decorado-una-gran-398214730.jpg",
                        descripcion = "Torta fresca con frutas de temporada y crema chantilly.",
                        descripcionDetallada = "Hermosa torta cuadrada decorada con una selección de frutas frescas de temporada como fresas, kiwis, duraznos y arándanos. Base de bizcocho esponjoso de vainilla, relleno de crema chantilly casera y decoración artesanal con frutas frescas. Perfecta para celebraciones de verano, cumpleaños y eventos al aire libre. Cada porción es una explosión de sabores frescos y naturales.",
                        rating = 4.7, reviews = 19, porciones = "10-15 personas", calorias = "320 cal/porción",
                        ingredientes = "Frutas frescas de temporada, crema chantilly, harina, huevos, azúcar, vainilla, gelatina",
                        categoryId = "tortas-cuadradas"
                    ),
                    // Tortas Circulares
                    ProductEntity(
                        id = "TT001", nombre = "Torta Circular de Vainilla", precio = 18990,
                        imagen = "https://wiltonenespanol.com/wp-content/uploads/2017/02/pastel-de-vainilla.jpg",
                        descripcion = "Torta tradicional de vainilla con buttercream y frutas frescas.",
                        descripcionDetallada = "Clásica torta circular de vainilla, elaborada con extracto de vainilla natural y decorada con buttercream suave. Capas de bizcocho esponjoso de vainilla, relleno de crema de vainilla y decoración elegante con frutas frescas de temporada. Un postre atemporal que nunca pasa de moda, perfecto para cualquier celebración.",
                        rating = 4.6, reviews = 18, porciones = "8-10 personas", calorias = "320 cal/porción",
                        ingredientes = "Vainilla natural, harina, huevos, azúcar, mantequilla, frutas frescas",
                        categoryId = "tortas-circulares"
                    ),
                    ProductEntity(
                        id = "TT002", nombre = "Torta Circular de Manjar", precio = 15990,
                        imagen = "https://www.elingenio.cl/productos/bizcocho-manjar-lucuma.jpg",
                        descripcion = "Torta circular con manjar casero y decoración elegante.",
                        descripcionDetallada = "Exquisita torta circular de manjar casero, elaborada con la receta tradicional chilena. Capas de bizcocho esponjoso, relleno generoso de manjar casero y decoración elegante con nueces y almendras. Un clásico de la repostería chilena que evoca los sabores de la infancia. Perfecta para celebraciones familiares y ocasiones especiales.",
                        rating = 4.9, reviews = 31, porciones = "8-10 personas", calorias = "380 cal/porción",
                        ingredientes = "Manjar casero, harina, huevos, azúcar, mantequilla, nueces, almendras",
                        categoryId = "tortas-circulares"
                    ),
                    ProductEntity(
                        id = "TT003", nombre = "Torta Circular de Frutilla", precio = 19990,
                        imagen = "https://www.annarecetasfaciles.com/files/tarta-de-fresas-y-nata-3.jpg",
                        descripcion = "Torta circular de frutillas frescas con crema chantilly.",
                        descripcionDetallada = "Deliciosa torta circular de frutillas frescas, elaborada con las mejores frutillas de temporada. Base de bizcocho esponjoso de vainilla, relleno de crema chantilly casera y decorada con frutillas frescas enteras y en rodajas. Un postre fresco y elegante que combina la dulzura natural de las frutillas con la suavidad de la crema. Perfecta para celebraciones de primavera y verano, cumpleaños y eventos especiales.",
                        rating = 4.7, reviews = 28, porciones = "15 personas", calorias = "320 cal/porción",
                        ingredientes = "Frutillas frescas, crema chantilly, harina, huevos, azúcar, vainilla, gelatina",
                        categoryId = "tortas-circulares"
                    ),
                    // Postres Individuales
                    ProductEntity(
                        id = "PI001", nombre = "Mousse de Chocolate", precio = 5990,
                        imagen = "https://images.aws.nestle.recipes/original/2024_10_18T11_53_16_badun_images.badun.es_4ed41e942636_mousse_de_chocolate_intenso.jpg",
                        descripcion = "Delicioso mousse de chocolate con decoración de frutas.",
                        descripcionDetallada = "Exquisito mousse de chocolate intenso, elaborado con chocolate premium y crema fresca. Textura suave y aterciopelada que se derrite en el paladar. Decorado con frutas frescas de temporada y virutas de chocolate. Perfecto como postre individual o para compartir en ocasiones especiales. Una experiencia de sabor que deleitará a los amantes del chocolate.",
                        rating = 4.5, reviews = 12, porciones = "1 persona", calorias = "280 cal/porción",
                        ingredientes = "Chocolate premium, crema de leche, huevos, azúcar, frutas frescas",
                        categoryId = "postres-individuales"
                    ),
                    ProductEntity(
                        id = "PI002", nombre = "Tiramisú Clásico", precio = 7990,
                        imagen = "https://www.kingarthurbaking.com/sites/default/files/2023-03/Tiramisu_1426.jpg",
                        descripcion = "Tiramisú tradicional italiano con café y mascarpone.",
                        descripcionDetallada = "Auténtico tiramisú italiano, elaborado siguiendo la receta tradicional. Capas de bizcocho savoiardi empapado en café espresso, crema de mascarpone suave y espolvoreado con cacao en polvo. Un postre elegante y sofisticado que transporta a las cafeterías de Italia. Perfecto para los amantes del café y la repostería italiana.",
                        rating = 4.8, reviews = 22, porciones = "1 persona", calorias = "320 cal/porción",
                        ingredientes = "Café espresso, mascarpone, cacao en polvo, bizcocho savoiardi, huevos, azúcar",
                        categoryId = "postres-individuales"
                    ),
                    // Productos Sin Azúcar
                    ProductEntity(
                        id = "PSA001", nombre = "Torta Sin Azúcar de Naranja", precio = 25990,
                        imagen = "https://santaisabel.vtexassets.com/arquivos/ids/447848-900-900?width=900&height=900&aspect=true",
                        descripcion = "Torta saludable sin azúcar con sabor a naranja natural.",
                        descripcionDetallada = "Deliciosa torta de naranja sin azúcar, elaborada con naranjas frescas y edulcorantes naturales. Perfecta para personas con diabetes o que buscan opciones más saludables. Base de bizcocho esponjoso de naranja, relleno de crema de naranja natural y decoración con gajos de naranja fresca. Un postre refrescante y saludable que no compromete el sabor.",
                        rating = 4.6, reviews = 15, porciones = "8-10 personas", calorias = "250 cal/porción",
                        ingredientes = "Naranja natural, edulcorante stevia, harina integral, huevos, aceite de oliva",
                        categoryId = "productos-sin-azucar"
                    ),
                    ProductEntity(
                        id = "PSA002", nombre = "Cheesecake de Maracuyá Sin Azúcar", precio = 12990,
                        imagen = "https://bechef.cl/wp-content/uploads/2022/02/CCMM-1.png",
                        descripcion = "Cheesecake de maracuyá sin azúcar, saludable y delicioso.",
                        descripcionDetallada = "Delicioso cheesecake de maracuyá sin azúcar, perfecto para quienes buscan un postre saludable sin comprometer el sabor. Elaborado con edulcorantes naturales como stevia y la frescura única del maracuyá natural. Base de galletas integrales sin azúcar, crema de queso suave y topping de maracuyá fresco. Ideal para personas con diabetes, dietas bajas en carbohidratos o simplemente para quienes prefieren opciones más saludables sin sacrificar el sabor.",
                        rating = 4.8, reviews = 22, porciones = "8 personas", calorias = "220 cal/porción",
                        ingredientes = "Queso crema, maracuyá natural, edulcorante stevia, galletas integrales sin azúcar, huevos, gelatina sin sabor",
                        categoryId = "productos-sin-azucar"
                    ),
                    // Pastelería Tradicional
                    ProductEntity(
                        id = "PT001", nombre = "Empanada de Manzana", precio = 1890,
                        imagen = "https://cocinachilena.cl/wp-content/uploads/2012/11/empanadas-manzana-3-scaled.jpg",
                        descripcion = "Empanada tradicional de manzana con canela y azúcar.",
                        descripcionDetallada = "Deliciosa empanada de manzana casera, elaborada con masa fresca y relleno de manzanas cortadas en cubos con canela, azúcar y un toque de limón. Horneada hasta obtener una textura dorada y crujiente. Un clásico de la repostería chilena que combina perfectamente con una taza de té o café. Ideal para la hora del té o como postre ligero.",
                        rating = 4.4, reviews = 28, porciones = "1 persona", calorias = "180 cal/porción",
                        ingredientes = "Manzana, canela, azúcar, masa de empanada, limón, mantequilla",
                        categoryId = "pasteleria-tradicional"
                    ),
                    ProductEntity(
                        id = "PT002", nombre = "Tarta de Santiago", precio = 11990,
                        imagen = "https://recetasdecocina.elmundo.es/wp-content/uploads/2025/03/tarta-de-santiago.jpg",
                        descripcion = "Tarta tradicional española con almendras y limón.",
                        descripcionDetallada = "Auténtica Tarta de Santiago, el postre más emblemático de Galicia. Elaborada con almendras molidas, huevos, azúcar y un toque de limón. Decorada con la cruz de Santiago en azúcar glass. Una tarta sin harina, perfecta para celíacos, con una textura húmeda y un sabor intenso a almendras. Un clásico de la repostería española que conquista paladares.",
                        rating = 4.8, reviews = 16, porciones = "6-8 personas", calorias = "320 cal/porción",
                        ingredientes = "Almendras molidas, limón, azúcar, huevos, azúcar glass",
                        categoryId = "pasteleria-tradicional"
                    ),
                    // Productos Sin Gluten
                    ProductEntity(
                        id = "PG001", nombre = "Brownie Sin Gluten", precio = 2990,
                        imagen = "https://www.justspices.es/media/recipe/brownie-chocolate.jpg",
                        descripcion = "Brownie delicioso sin gluten con chocolate premium.",
                        descripcionDetallada = "Exquisito brownie sin gluten elaborado con chocolate premium y harina de arroz. Textura húmeda y densa en el centro, con una corteza crujiente en la superficie. Perfecto para personas celíacas o que siguen una dieta sin gluten. Decorado con nueces y chips de chocolate. Un postre que no compromete el sabor ni la textura tradicional del brownie.",
                        rating = 4.5, reviews = 14, porciones = "4-6 personas", calorias = "300 cal/porción",
                        ingredientes = "Chocolate premium, harina de arroz, huevos, azúcar, mantequilla, nueces",
                        categoryId = "productos-sin-gluten"
                    ),
                    ProductEntity(
                        id = "PG002", nombre = "Pan Sin Gluten", precio = 3590,
                        imagen = "https://imag.bonviveur.com/pan-sin-gluten.jpg",
                        descripcion = "Pan artesanal sin gluten con semillas y frutos secos.",
                        descripcionDetallada = "Pan artesanal sin gluten elaborado con una mezcla de harinas especiales, semillas de girasol, chía y sésamo, además de frutos secos como nueces y almendras. Textura esponjosa y sabor natural. Perfecto para el desayuno o acompañar cualquier comida. Ideal para personas celíacas o que buscan opciones más saludables sin comprometer el sabor.",
                        rating = 4.3, reviews = 11, porciones = "8-10 rebanadas", calorias = "200 cal/porción",
                        ingredientes = "Harina sin gluten, semillas de girasol, chía, sésamo, nueces, almendras, levadura",
                        categoryId = "productos-sin-gluten"
                    ),
                    // Productos Veganos
                    ProductEntity(
                        id = "PV001", nombre = "Torta Vegana de Chocolate", precio = 22990,
                        imagen = "",
                        descripcion = "Torta de chocolate 100% vegana con ingredientes naturales.",
                        descripcionDetallada = "Exquisita torta de chocolate 100% vegana, elaborada sin productos de origen animal. Utiliza chocolate vegano, leche de almendras, azúcar de coco y harina integral. Decorada con crema de coco y frutas frescas. Perfecta para veganos, vegetarianos o cualquier persona que busque opciones más saludables y sostenibles sin comprometer el sabor delicioso del chocolate.",
                        rating = 4.7, reviews = 20, porciones = "8-10 personas", calorias = "280 cal/porción",
                        ingredientes = "Chocolate vegano, harina integral, leche de almendras, azúcar de coco, aceite de coco",
                        categoryId = "productos-veganos"
                    ),
                    ProductEntity(
                        id = "PV002", nombre = "Galletas Veganas de Avena", precio = 890,
                        imagen = "https://enlacemalhecho.aproposito.com/para-el-profe.jpg",
                        descripcion = "Galletas saludables de avena con pasas y canela.",
                        descripcionDetallada = "Deliciosas galletas veganas de avena, elaboradas con avena integral, pasas, canela y endulzadas con azúcar de coco. Sin huevos, leche ni mantequilla. Perfectas para el desayuno, merienda o como snack saludable. Textura crujiente por fuera y suave por dentro. Ideales para veganos, vegetarianos o cualquier persona que busque opciones más saludables y nutritivas.",
                        rating = 4.4, reviews = 17, porciones = "12 galletas", calorias = "120 cal/porción",
                        ingredientes = "Avena integral, pasas, canela, aceite de coco, azúcar de coco, harina de avena",
                        categoryId = "productos-veganos"
                    ),
                    // Tortas Especiales
                    ProductEntity(
                        id = "TE001", nombre = "Torta Especial de Cumpleaños", precio = 29990,
                        imagen = "https://images.unsplash.com/photo-1563729784474-d77dbb933a9e?w=800",
                        descripcion = "Torta personalizada para cumpleaños con decoración especial.",
                        descripcionDetallada = "Torta especial de cumpleaños personalizada según tus gustos y preferencias. Elaborada con los mejores ingredientes y decorada con crema, frutas frescas, chocolates y elementos decorativos temáticos. Perfecta para hacer de tu cumpleaños un día inolvidable. Incluye decoración personalizada con el nombre del cumpleañero y velas. Una experiencia única que combina sabor excepcional con presentación espectacular.",
                        rating = 4.9, reviews = 35, porciones = "12-15 personas", calorias = "400 cal/porción",
                        ingredientes = "Harina premium, huevos frescos, azúcar, mantequilla, crema chantilly, frutas frescas, chocolates, decoración personalizada",
                        categoryId = "tortas-especiales"
                    ),
                    ProductEntity(
                        id = "TE002", nombre = "Torta Especial de Boda", precio = 79990,
                        imagen = "https://bodasyweddings.com/wp-content/uploads/2015/04/Si-prefieres-un-diseno-simple-hazlo-inolvidable.jpg",
                        descripcion = "Torta elegante para bodas con decoración premium.",
                        descripcionDetallada = "Exquisita torta de boda elaborada con la máxima elegancia y sofisticación. Diseño personalizado según el estilo de la boda, con decoración artesanal que incluye flores comestibles, detalles en fondant y acabados de lujo. Perfecta para hacer de tu día especial un momento inolvidable. Cada detalle está cuidadosamente elaborado para crear una obra de arte comestible que refleje la personalidad de los novios.",
                        rating = 5.0, reviews = 42, porciones = "20-25 personas", calorias = "350 cal/porción",
                        ingredientes = "Harina premium, huevos frescos, azúcar, mantequilla, crema chantilly, fondant, flores comestibles, decoración artesanal",
                        categoryId = "tortas-especiales"
                    )
                )
                productDao.insertarTodos(productos)
            }
        }
    }
}

