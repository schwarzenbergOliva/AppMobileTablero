package com.example.tableroapp

object CardsRepository {
    private val _cards = mutableListOf(
        CardData(1, "Tarea Pendiente", "Esta es una tarea que necesita ser completada.", EstadoCard.TODO),
        CardData(2, "Tarea en Progreso", "Esta tarea está siendo trabajada actualmente.", EstadoCard.IN_PROGRESS),
        CardData(3, "Tarea Completada", "Esta tarea ha sido finalizada exitosamente.", EstadoCard.DONE),
        CardData(4, "Nueva Tarea", "Esta es una nueva tarea que debe ser iniciada.", EstadoCard.TODO)
    )
    
    fun getAllCards(): List<CardData> = _cards.toList()
    
    fun addCard(card: CardData) {
        _cards.add(card)
    }
    
    fun updateCard(updatedCard: CardData) {
        val index = _cards.indexOfFirst { it.id == updatedCard.id }
        if (index != -1) {
            _cards[index] = updatedCard
        }
    }
    
    fun getCardById(id: Int): CardData? {
        return _cards.find { it.id == id }
    }
    
    fun getNextId(): Int {
        return (_cards.maxOfOrNull { it.id } ?: 0) + 1
    }
}

