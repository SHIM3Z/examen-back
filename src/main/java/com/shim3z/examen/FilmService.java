package com.shim3z.examen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shim3z.examen.modelo.Personaje;
import com.shim3z.examen.modelo.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class FilmService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "https://swapi.dev/api/";

    // Método para obtener solo los nombres de las películas
    public List<Pelicula> getAllPeliculas() {
        String url = BASE_URL + "films/";
        String filmsResponse = restTemplate.getForObject(url, String.class);

        List<Pelicula> peliculas = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(filmsResponse);
            JsonNode results = root.path("results");
            for (JsonNode node : results) {
                Pelicula pelicula = new Pelicula();
                pelicula.setId(node.path("episode_id").asInt());
                pelicula.setTitulo(node.path("title").asText());
                pelicula.setResumen(node.path("opening_crawl").asText());
                peliculas.add(pelicula);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return peliculas;
    }

    // Método para obtener los nombres completos de los personajes de una película específica
    public List<Personaje> getPersonajesPorPeli(int peliId) {
        String url = BASE_URL + "films/" + peliId + "/";
        String filmResponse = restTemplate.getForObject(url, String.class);

        List<Personaje> characterNames = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(filmResponse);
            JsonNode characters = root.path("characters");
            for (JsonNode characterUrlNode : characters) {
                String characterUrl = characterUrlNode.asText();
                String characterResponse = restTemplate.getForObject(characterUrl, String.class);
                JsonNode characterRoot = mapper.readTree(characterResponse);
                Personaje character = new Personaje();
                character.setName(characterRoot.path("name").asText());
                characterNames.add(character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return characterNames;
    }
}