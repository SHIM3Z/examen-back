package com.shim3z.examen;

import com.shim3z.examen.modelo.Personaje;
import com.shim3z.examen.modelo.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class FilmController {
    @Autowired
    private FilmService filmService;

    // Endpoint para obtener solo los nombres de las películas
    @GetMapping("/pelis")
    public List<Pelicula> getAllPeliculas() {
        return filmService.getAllPeliculas();
    }

    // Endpoint para obtener los nombres completos de los personajes de una película específica
    @GetMapping("/pelis/{peliId}/personajes")
    public List<Personaje> getPersonajesPorPeli(@PathVariable int peliId) {
        return filmService.getPersonajesPorPeli(peliId);
    }
}
