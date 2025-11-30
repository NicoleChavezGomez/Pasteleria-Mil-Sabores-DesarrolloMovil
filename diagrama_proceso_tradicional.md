# Diagrama: Proceso Tradicional - Pastelería Mil Sabores

```mermaid
flowchart TD
    A[Cliente quiere hacer un pedido] --> B{¿Cómo contacta?}
    B -->|WhatsApp| C[Envía mensaje a la pastelería]
    B -->|Llamada telefónica| D[Llama a la pastelería]
    B -->|Visita presencial| E[Va a la pastelería]
    
    C --> F[Pastelero busca en cuaderno/catálogo físico]
    D --> F
    E --> F
    
    F --> G{¿Producto disponible?}
    G -->|No está en catálogo| H[Cliente debe esperar respuesta]
    G -->|Está en catálogo| I[Pastelero consulta precio manualmente]
    
    H --> J[Pastelero verifica disponibilidad]
    J --> I
    
    I --> K[Cliente confirma pedido]
    K --> L[Pastelero anota pedido en cuaderno]
    L --> M[Cliente espera preparación]
    M --> N[Cliente recoge pedido]
    
    style A fill:#ffcccc
    style F fill:#ffffcc
    style L fill:#ffffcc
    style N fill:#ccffcc
    
    classDef problema fill:#ff9999,stroke:#ff0000,stroke-width:2px
    class F,L problema
```

## Problemas del Proceso Tradicional

- **Gestión manual**: Todo se anota en cuadernos físicos
- **Catálogo limitado**: Cliente no puede ver todos los productos fácilmente
- **Comunicación lenta**: Depende de respuesta por WhatsApp
- **Sin disponibilidad en tiempo real**: Cliente no sabe si hay stock
- **Precios no transparentes**: Cliente debe consultar cada vez
- **Sin historial digital**: Pedidos anteriores se pierden o son difíciles de encontrar

