package gameZeldiablo.Zeldiablo;

import java.net.http.HttpClient;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Prompt {
    private static final String PROMPT = """
[AI-SHORTCUT]Tu es un assistant qui génère des phrases courtes pour un joueur de MMORPG.

Contexte : Le joueur vient de ramasser l'objet "@1" dans un donjon.

Génère UNE SEULE phrase courte (4-6 mots maximum) que le joueur pourrait dire spontanément en récupérant cet objet.

La phrase doit être :
- Naturelle et authentique
- Adaptée au nom de l'objet
- Dans le style d'un joueur de jeu vidéo
- En français

Réponds UNIQUEMENT avec la phrase, sans guillemets ni commentaires.
    """;
    
    /**
     * Demande à GPT de générer un message pour le joueur lorsqu'il ramasse un objet.
     * @param itemName Le nom de l'objet ramassé par le joueur.
     * @param callback La fonction de rappel qui sera appelée avec le message généré.
     */
    public static void askGptForMsgWhenPickingItem(String itemName, java.util.function.Consumer<String> callback) {
        String prompt = PROMPT.replace("@1", itemName);
        String url = "https://rayanestaszewski.fr/gpt?prompt=" + URLEncoder.encode(prompt, StandardCharsets.UTF_8);
        
        // Exécution en arrière-plan
        java.util.concurrent.CompletableFuture.supplyAsync(() -> {
            HttpClient httpClient = HttpClient.newHttpClient();
            try {
                var request = java.net.http.HttpRequest.newBuilder()
                        .uri(java.net.URI.create(url))
                        .header("Content-Type", "application/json")
                        .build();

                var response = httpClient.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
                String rep = response.body();

                // enlève les guillemets autour de la réponse et les \n
                rep = rep.replace("\"", "").replace("\\n", "").trim();
                return rep;
            } catch (Exception e) {
                e.printStackTrace();
                return ""; // Erreur : le joueur ne dit rien
            }
        }).thenAccept(callback);
    }
}
