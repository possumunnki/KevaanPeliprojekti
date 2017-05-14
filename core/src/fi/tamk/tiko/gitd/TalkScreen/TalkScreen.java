package fi.tamk.tiko.gitd.TalkScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

import fi.tamk.tiko.gitd.GameScreen.GameScreen;
import fi.tamk.tiko.gitd.MapScreen.MapScreen;
import fi.tamk.tiko.gitd.MyGdxGame;

import java.util.ArrayList;

/**
 * Created by possumunnki on 17.4.2017.
 */

public class TalkScreen implements Screen {
    private MyGdxGame host;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    ArrayList<Speech> speeches;
    private float screenWidth;
    private float screenHeight;

    private final int BOTH = 0;
    private final int GRANDMA = 1;
    private final int LIGHT_DOLL = 2;
    private final int KEKKONEN = 3;
    private final int GRANDMA2 = 4;

    private String text;
    private SkipActor skip;
    private int currentSpeech = 0;
    private Stage skipStage;

    public TalkScreen(MyGdxGame host) {
        this.host = host;
        screenWidth = host.SCREEN_WIDTH * 100f;
        screenHeight = host.SCREEN_HEIGHT * 100f;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                screenWidth,
                screenHeight);
        this.batch = host.getSpriteBatch();
        skipStage = new Stage(new FillViewport(screenWidth,
                screenHeight),
                batch);
        skip = new SkipActor(screenWidth * 8 / 10, screenHeight * 0, host);

        skipStage.addActor(skip);
        Gdx.input.setInputProcessor(skipStage);
        speeches = new ArrayList<Speech>();
        addTalk();
    }

    @Override
    public void show() {
        Gdx.app.log("TalkScreen:", "show");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // add stage to add skip button
        try {
            speeches.get(currentSpeech).draw(batch, screenWidth, screenHeight);
        } catch (Exception e) {
            // draws last speach of the array
            speeches.get(speeches.size() - 1).draw(batch, screenWidth, screenHeight);
        }
        batch.end();
        skipStage.act();
        skipStage.draw();

        // when player touches the screen, it shows next speech
        if (Gdx.input.justTouched()) {
            currentSpeech++;
        }

        if (currentSpeech == speeches.size() || skip.getTouch()) {
            if (host.levelProgression == host.END) {
                // unlocks next stage
                host.unlockStage(host.getCurrentStage());
                // sets next stage as a current stage
                host.setCurrentStage(host.getCurrentStage() + 1);
                // saves cleared stages
                host.saveUnlockedStages(host.getCurrentStage());
                // sets unlocked stages
                host.setUnlockedStages(host.getUnlockedStages());
                host.setScreen(new MapScreen(host));
            } else if (host.levelProgression == host.BEGINNING) {
                host.setScreen(new GameScreen(host));
            }

        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        for (int i = 0; i < speeches.size(); i++) {
            speeches.get(i).dispose();
        }
        skip.dispose();
        skipStage.dispose();
        Gdx.app.log("TalkScreen", "disposed");
    }

    private void addTalk() {
        if (host.getCurrentStage() == 1) {
            if(host.levelProgression == host.BEGINNING) {
                text = "Herran pieksut!\nMitä ihmettä tapahtui?\nMiksi kaikki on niin valtavaa?";
                speeches.add(new Speech(GRANDMA, text));

                text = "Huminaa muminaa";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "Kääk! Hetkinen,etkös sinä \n ole se minkä takia minä\n " +
                        "tähän kartanoon murtauduin?";
                speeches.add(new Speech(GRANDMA, text));

                text = "Hums mums";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "Sinähän leijut! Ja puhut!\nTai mumiset.. ";
                speeches.add(new Speech(GRANDMA, text));

                text = "Joko tämä tönö on kirottu\ntai unohdin sittenkin \nottaa lääkkeeni.";
                speeches.add(new Speech(GRANDMA, text));

                text = "...";
                speeches.add(new Speech(BOTH, text));

                text = "Eiköhän lähdetä\n kiireen vilkkaa pois täältä.";
                speeches.add(new Speech(GRANDMA, text));

            } else if(host.levelProgression == host.END) {
                text = "Herranjestas,\n eikö tämä huone pääty koskaan?";
                speeches.add(new Speech(GRANDMA, text));

                text = "Muminaa.";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "...";
                speeches.add(new Speech(GRANDMA, text));

                text = "Pettävätkö lasini vai\noliko tuo epämääräinen sumu\n tuossa aikaisemmin?";
                speeches.add(new Speech(GRANDMA, text));

                text = "Hei hei hei mehän\n imeydymme sitä kohti!\n kääääääääääk!";
                speeches.add(new Speech(GRANDMA, text));

                text = "Aggressiivista muminaa.";
                speeches.add(new Speech(LIGHT_DOLL, text));
            }
        } else if (host.getCurrentStage() == 2) {
            if(host.levelProgression == host.BEGINNING) {
                text = "Mitä juuri tapahtui?\n Olo on kuin mankeloidulla";
                speeches.add(new Speech(GRANDMA, text));

                text = "Missähän me nyt mahdamme \nluurata?";
                speeches.add(new Speech(GRANDMA, text));

                text = "Kaikki näyttää pimeässä ja\n rotan kokoisena ihan \nsamalta.";
                speeches.add(new Speech(GRANDMA, text));

                text = "Hum—";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "Hys nyt mumiseva paperipaino!\n Kuuletko tuon myhäilyn?";
                speeches.add(new Speech(GRANDMA, text));

                text = "Leijuvia auringonjumalia, \nvihaisia voodoo-nukkeja, \nepämääräistä käkätystä...";
                speeches.add(new Speech(GRANDMA, text));

                text = "Miksi minä edes tulin tänne?";
                speeches.add(new Speech(GRANDMA, text));

                text = "...";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "Ai niin...";
                speeches.add(new Speech(GRANDMA, text));
            } else if(host.levelProgression == host.END) {
                text = "...mainiota";
                speeches.add(new Speech(KEKKONEN, text));

                text = "HA-HA-HA-HAAMU!";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Hei! Olisit sinäkin hieman\nkalpea jos olisit ollut\n jumissa kirotussa ja ";
                speeches.add(new Speech(KEKKONEN, text));

                text = "ikuisesti pimeässä kartanossa\n monta kymmentä vuotta!";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Olet siis oikeasti \nUrho Kekkonen?\n Etkä ole haamu?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Ainut ja oikea.Tai oikeastaan\nolen vain nukke, johon tämä\nkartano on puhaltanut elämän.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Ehkä on parempi\netten kyseenalaista tuota.";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Mainiota.\nAsiasta seuraavaan, oletan\nettä haluat pois täältä.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Voin auttaa tietä pakenemaan,\nmutta teidän täytyy\nluottaa minuun.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Mitä tarkoitat?";
                speeches.add(new Speech(GRANDMA2,text));

                text = "Nopein reitti eteiseen on\nhypätä näiden portaalien läpi.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Uskoisin-- Öhöm...tai siis,\n olen täysin varma, että" +
                        "\ntämä tässä vie meidät perille.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Ei kuulosta kauhean\nvakuuttavalta...";
                speeches.add(new Speech(GRANDMA2, text));

                text = " Luota vain minuun!";
                speeches.add(new Speech(KEKKONEN, text));

                text = "...";
                speeches.add(new Speech(BOTH,text));

                text = "Ei kai tässä muukaan auta.";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Mainiota.";
                speeches.add(new Speech(KEKKONEN, text));
            }
        } else if(host.getCurrentStage() == 3) {
            if(host.levelProgression == host.BEGINNING) {
                text = "Hetkinen, tämä ei näytä\nollenkaan eteiseltä...";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Hupsista—Tai siis, mainiota..";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Mitä tapahtui? Missä olemme?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Hum—";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "Päädyimme juuri sinne\n mihin oli tarkoituskin:\n keittiöön!";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Eikös meidän pitänyt hankkia\n itsemme ulos täältä,\n" +
                        " ei vielä enemmän eksyksiin?";
                speeches.add(new Speech(GRANDMA2,text));

                text = "Muistinkin tarvitsevani\njotakin tämän keittiön\nviereisestä varastosta!";
                speeches.add(new Speech(KEKKONEN, text));

                text = " Niin, kyllä, jotakin\n mainiota, mikä auttaa\n meitä tehtävässämme.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "...";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "Mikset sitten vienyt\nmeitä suoraan varastoon?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Kaikki aikanaan! \nTätä aluetta kartanoa vartioi\n hyvin julma peto";
                speeches.add(new Speech(KEKKONEN, text));

                text = "ja tarvitsemme kulkuvälineen,\njotta voimme paeta siltä.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Mistä me tähän hätään—";
                speeches.add(new Speech(GRANDMA2, text));

                text = "HOI ELMERI!";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Anteeksi mitä?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Tämä tässä on Elmeri.\nHän on luotettavin, ja myös\n" +
                        " ainoa ratsu tässä kartanossa.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Hypätkää kyytiin niin\nlähdemme heti! Voin jo kuulla\n" +
                        "sen karmivan elukan tepastelun.";
                speeches.add(new Speech(KEKKONEN, text));

            } else if(host.levelProgression == host.END) {
                text = "Herran pieksut!\nOnnistuimmeko kadottamaan\nsen?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Hetkeksi ainakin.\nSentään pääsimme varastolle\n asti yhtenä kappaleena.";
                speeches.add(new Speech(KEKKONEN, text));
            }
        } else if(host.getCurrentStage() == 4){
            if(host.levelProgression == host.BEGINNING) {
                text = "Noniin, eiköhän koluta\ntämä varasto läpi.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Etkö vieläkään suostu\n kertomaan mikä siellä\n on niin tärkeää,";
                speeches.add(new Speech(GRANDMA2, text));

                text = "että pitää varta vasten\n tulla kissan syötäväksi?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Sanoinhan, että sinun tulee\n vain luottaa minuun!";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Olenko koskaan aiemmin\npettänyt luottamustasi?";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Humin--";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "Sieltä se pirun mirri\n taas tulee!\nVipinää kinttuihin Elmeri!";
                speeches.add(new Speech(KEKKONEN, text));
            } else if(host.levelProgression == host.END) {
                text = "kääk!\nEiköh tämä koskaan pääty?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Mainiota! Löysin sen!";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Joko nyt kertoisit...";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Ei aikaa!\nHypätkää tähän portaaliin!";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Tällä...kin kertaa olen\nvarma, että se vie meidät\n" +
                        "juuri sinne minne pitääkin!";
                speeches.add(new Speech(KEKKONEN, text));
            }
        } else if(host.getCurrentStage() == 5) {
            if(host.levelProgression == host.BEGINNING) {
                text = "Hei! Sehän onnistui!\nMainiota...";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Tämä näyttääkin jo melko\ntutulta.";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Mikä tuo ääni oli?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Kartanon riivanneet henget\neivät taida pitää siitä, että\n" +
                        "olemme näin lähellä ulko-ovea.";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "...";
                speeches.add(new Speech(GRANDMA2, text));
                speeches.add(new Speech(KEKKONEN, text));

                text = "Mikset kertonut,\n että osaat puhua?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "(Epämääräinen karjaisu \nkaukaisuudesta)";
                speeches.add(new Speech(BOTH,text));

                text = "Voimme puhua tästä\n myöhemmin, nyt on aika\n juosta!";
                speeches.add(new Speech(KEKKONEN, text));
            }
        }
    }

}
