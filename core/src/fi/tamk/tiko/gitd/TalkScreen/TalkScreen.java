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
        if(host.locale == host.FINNISH) {
            addTalkFin();
        } else if(host.locale == host.ENGLISH) {
            addTalkEng();
        }

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

    private void addTalkFin() {
        if (host.getCurrentStage() == 1) {
            if(host.levelProgression == host.BEGINNING) {
                text = "Herran pieksut!\nMitä ihmettä tapahtui?\nMiksi kaikki on niin valtavaa?";
                speeches.add(new Speech(GRANDMA, text));

                text = "(Huminaa muminaa)";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "Kääk! Hetkinen,etkös sinä \n ole se minkä takia minä\n " +
                        "tähän kartanoon murtauduin?";
                speeches.add(new Speech(GRANDMA, text));

                text = "(Hums mums)";
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

                text = "(Muminaa)";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "...";
                speeches.add(new Speech(GRANDMA, text));

                text = "Pettävätkö lasini vai\noliko tuo epämääräinen sumu\n tuossa aikaisemmin?";
                speeches.add(new Speech(GRANDMA, text));

                text = "Hei hei hei mehän\n imeydymme sitä kohti!\n kääääääääääk!";
                speeches.add(new Speech(GRANDMA, text));

                text = "(Aggressiivista muminaa)";
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

    private void addTalkEng() {
        if (host.getCurrentStage() == 1) {

            if(host.levelProgression == host.BEGINNING) {
                text = "Oh my lord! What in the\n world just happened?\nWhy is everything so huge?";
                speeches.add(new Speech(GRANDMA, text));

                text = "(Humming and mumbling)";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "AAH! Wait a second, aren’t\nyou the reason why I broke into\n" +
                        "this mansion in the first place?";
                speeches.add(new Speech(GRANDMA, text));

                text = "(Humble mumble)";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "You’re floating!\nAnd talking! Or mumbling\nrather... ";
                speeches.add(new Speech(GRANDMA, text));

                text = "Either this mansion is\n cursed or I did forget\n to take my medicine.";
                speeches.add(new Speech(GRANDMA, text));

                text = "...";
                speeches.add(new Speech(BOTH, text));

                text = "Let’s get out of here.";
                speeches.add(new Speech(GRANDMA, text));

            } else if(host.levelProgression == host.END) {

                text = "Dear me,\nwill this room never end?";
                speeches.add(new Speech(GRANDMA, text));

                text = "(Humming)";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "...";
                speeches.add(new Speech(GRANDMA, text));

                text = "Are my glasses deceiving me\nor was that suspicious fog\nthere before?";
                speeches.add(new Speech(GRANDMA, text));

                text = "Hey! We’re being\nsucked towards it!\nAAAAAAAAAAH!";
                speeches.add(new Speech(GRANDMA, text));

                text = "(Aggressive humming)";
                speeches.add(new Speech(LIGHT_DOLL, text));
            }
        } else if (host.getCurrentStage() == 2) {

            if(host.levelProgression == host.BEGINNING) {
                text = "What just happened?\nI’m feeling all mangled up.";
                speeches.add(new Speech(GRANDMA, text));

                text = "And where might we be now??\nEverything looks exactly";
                speeches.add(new Speech(GRANDMA, text));

                text = "the same when you’re in the dark\n and the size of a rat.";
                speeches.add(new Speech(GRANDMA, text));

                text = "Hum--";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "Quiet now you mumbling\n paperweight! Can you\nhear that cackling??";
                speeches.add(new Speech(GRANDMA, text));

                text = "Floating sun gods,\nangry voodoo dolls,\n peculiar chuckling...";
                speeches.add(new Speech(GRANDMA, text));

                text = "Why did I even come here?";
                speeches.add(new Speech(GRANDMA, text));

                text = "...";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "Oh... Right...";
                speeches.add(new Speech(GRANDMA, text));

            } else if(host.levelProgression == host.END) {

                text = "…excellent.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "G-G-G-GHOST!";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Hey! You would be slightly\npale as well if you had\nbeen stuck in a cursed";
                speeches.add(new Speech(KEKKONEN, text));

                text = "and eternally dark\nmansion for several decades!";
                speeches.add(new Speech(KEKKONEN, text));

                text = "So you’re actually\nPresident Urho Kekkonen?\nAnd you’re not a ghost?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "The one and only.\nOr rather, I’m but a doll";
                speeches.add(new Speech(KEKKONEN, text));

                text = " who has had life\n blown into him by\nthis mansion.";
                speeches.add(new Speech(KEKKONEN, text));

                text = " Maybe it’s best\n I don’t question that.";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Excellent. Moving along,\n I expect you are looking\n" +
                        " for a way out of here.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "I can help you escape,\nbut you have to trust me.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "What do you mean?";
                speeches.add(new Speech(GRANDMA2,text));

                text = "The fastest way to the\nfront door is through these\nportals.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "I believe—Ahem… I mean,\nI am absolutely positive that this\n one right here will take us there.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "That doesn’t sound\n too convincing...";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Just put your faith\n in me!";
                speeches.add(new Speech(KEKKONEN, text));

                text = "...";
                speeches.add(new Speech(BOTH,text));

                text = "I guess I don’t have a choice.";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Excellent.";
                speeches.add(new Speech(KEKKONEN, text));
            }
        } else if(host.getCurrentStage() == 3) {

            if(host.levelProgression == host.BEGINNING) {
                text = " Wait a second, this doesn’t\nlook anything like the\nentrance…";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Oopsie—I mean, excellent...";
                speeches.add(new Speech(KEKKONEN, text));

                text = "What happened? Where are we?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Hum--";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "We ended up exactly where\n we were supposed to:\n the kitchen!";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Weren’t we supposed to get\n ourselves out of here instead\n" +
                        " of being even more lost?";
                speeches.add(new Speech(GRANDMA2,text));

                text = "I remembered I needed\n something from the storage\n" +
                        " room next to this kitchen!";
                speeches.add(new Speech(KEKKONEN, text));

                text = " Oh yes,\nsomething excellent, that\nwill help us in our mission.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "...";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "why didn’t you take us\n straight to the storage room?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "All in good time! This\npart of the mansion is guarded\n" +
                        "by an extremely savage beast";
                speeches.add(new Speech(KEKKONEN, text));


                text = "and we need a ride in order to\nescape it.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Where on earth do we--";
                speeches.add(new Speech(GRANDMA2, text));

                text = "OI! ELMERI!";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Excuse me?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "This right here is Elmeri.\nHe is the most reliable,\n and also the only,";
                speeches.add(new Speech(KEKKONEN, text));

                text = " steed this mansion has to\n offer.";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Jump right on and we will leave\nimmediately! I can already hear\n" +
                        "the the foul creature’s footsteps.";
                speeches.add(new Speech(KEKKONEN, text));

            } else if(host.levelProgression == host.END) {

                text = "Holy moly!\n Did we manage to lose it??";
                speeches.add(new Speech(GRANDMA2, text));

                text = "For now. At least we made it\nto the storage room in one\npiece.";
                speeches.add(new Speech(KEKKONEN, text));
            }
        } else if(host.getCurrentStage() == 4){
            if(host.levelProgression == host.BEGINNING) {


                text = "Okay, let us turn this room\nupside down, shall we?";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Are you still unwilling to\n tell us what’s important enough\n" +
                        "to get all of us eaten by a cat?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "I told you you just needed\n to have faith in me!";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Have I ever let you down?";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Hum--";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "There’s that damned puss again!\nGiddy up Elmeri!";
                speeches.add(new Speech(KEKKONEN, text));

            } else if(host.levelProgression == host.END) {

                text = "AAH!\nWill this never end?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "Excellent! I found it!";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Will you now tell us...";
                speeches.add(new Speech(GRANDMA2, text));

                text = "There is no time!\nJump into this portal!";
                speeches.add(new Speech(KEKKONEN, text));

                text = "I’m positive that this time...\ntoo it will take us exactly\n" +
                        "where it is supposed to!";
                speeches.add(new Speech(KEKKONEN, text));
            }
        } else if(host.getCurrentStage() == 5) {
            if(host.levelProgression == host.BEGINNING) {

                text = "Hey! It worked! Excellent...";
                speeches.add(new Speech(KEKKONEN, text));

                text = "Well this looks rather familiar.";
                speeches.add(new Speech(GRANDMA2, text));

                text = "";
                speeches.add(new Speech(BOTH, text));

                text = "What was that sound?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "The spirits which have cursed\nthismansion must not like us\n" +
                        "getting this close to the front door.";
                speeches.add(new Speech(LIGHT_DOLL, text));

                text = "...";
                speeches.add(new Speech(GRANDMA2, text));
                speeches.add(new Speech(KEKKONEN, text));

                text = "Why didn’t you tell us you can\nspeak?";
                speeches.add(new Speech(GRANDMA2, text));

                text = "I did try to, but you never\nbothered to listen.";
                speeches.add(new Speech(LIGHT_DOLL,text));

                text = "(Odd roar from the distance)";
                speeches.add(new Speech(BOTH,text));

                text = "We can talk about this later,\nit is time to run!";
                speeches.add(new Speech(KEKKONEN, text));
            }
        }
    }

}
