package com.cybertivity.omgblockz;

import com.cubes.*;
import com.cybertivity.omgblockz.blocks.*;
import com.cybertivity.omgblockz.worlds.*;
import com.cybertivity.omgblockz.dimensions.*;
import com.cybertivity.omgblockz.utility.*;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.*;
import com.jme3.input.controls.*;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.*;
import com.jme3.post.*;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.*;
import com.jme3.scene.shape.*;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.system.AppSettings;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;
import jme3utilities.TimeOfDay;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import jme3utilities.sky.SkyControl;

public class Main extends SimpleApplication implements ActionListener {

    private final Vector3Int terrainSize = new Vector3Int(100, 30, 100);
    private BulletAppState bulletAppState;
    private CharacterControl playerControl;
    private Vector3f walkDirection = new Vector3f();
    private boolean[] arrowKeys = new boolean[4];
    private static FileHandler fh = null;
    private CubesSettings cubesSettings;
    private BlockTerrainControl blockTerrain;
    private Node terrainNode;
    private static final int BLOCK_SIZE = 4;
    private MyBlockManager blockManager;
    TimeOfDay timeOfDay;
    private static final Logger log = Logger.getLogger(Chunk.class.getName());

    private static void ConfigureLogging() {
        Logger l = Logger.getLogger("");
        fh.setFormatter(new SimpleFormatter());
        l.addHandler(fh);
        l.setLevel(Level.CONFIG);
    }

    public Main() {
        settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("OMG! Blockz!");
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
        ConfigureLogging();
    }

    @Override
    public void simpleInitApp() {
        InitCubes();
        SetupSky();
        //AttachTestBox();
        initNewWorld(null, "D:\\temp\\OMGBlockz", "Test1");
        initCrossHairs();
        initMark();
        initKeys();
        initializeEnvironment(this);
        initializeWater(this);
        //initPlayer();
        Vector3f cameraLocation = new Vector3f(0, 20, 0);
        cam.setLocation(cameraLocation);
        //cam.lookAtDirection(new Vector3f(64 * BLOCK_SIZE, camHeight / 3, 64 * BLOCK_SIZE), Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(150);
        cam.setFrustumFar(4000);

        //setDisplayStatView(false);
    }

//    @Override
//    public void simpleUpdate(float tpf) {
//        //TODO: add update code
//    }
//
//    @Override
//    public void simpleRender(RenderManager rm) {
//        //TODO: add render code
//    }
    private void AttachChunks(Chunk[][] chunks, int arrayBoundsX, int arrayBoundsZ) {
        for (int x = 0; x < arrayBoundsX; x++) {
            for (int z = 0; z < arrayBoundsZ; z++) {
                AttachChunk(chunks[x][z]);
            }
        }
    }

    private void AttachChunk(Chunk chunk) {
        if (chunk != null) {
            //for every X,Z in chunk...
            Coordinate3D blockCoordinates = new Coordinate3D(0, 0, 0);
            for (int xOffset = 0; xOffset < Chunk.CHUNK_WIDTH_IN_BLOCKS; xOffset++) {
                for (int zOffset = 0; zOffset < Chunk.CHUNK_WIDTH_IN_BLOCKS; zOffset++) {
                    blockCoordinates.x = (chunk.getChunkCoordinateX() * Chunk.CHUNK_WIDTH_IN_BLOCKS) + xOffset;
                    blockCoordinates.z = (chunk.getChunkCoordinateZ() * Chunk.CHUNK_WIDTH_IN_BLOCKS) + zOffset;
                    for (int y = 0; y < chunk.getYMax(); y++) {
                        BlockBase block = chunk.getBlock(xOffset, y, zOffset);
                        if (block != null) { //null=air
                            blockTerrain.setBlock(blockCoordinates.x, y, blockCoordinates.z, block);
                        }
                    }
                }
            }
        }
    }
    private static final Vector3f lightDirection = new Vector3f(-0.8f, -1, -0.8f).normalizeLocal();

    public static void initializeEnvironment(SimpleApplication simpleApplication) {
        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.setDirection(lightDirection);
        directionalLight.setColor(new ColorRGBA(1f, 1f, 1f, 1.0f));
        simpleApplication.getRootNode().addLight(directionalLight);
        //simpleApplication.getRootNode().attachChild(SkyFactory.createSky(simpleApplication.getAssetManager(), "Textures/cubes/sky.jpg", true));

        DirectionalLightShadowRenderer directionalLightShadowRenderer = new DirectionalLightShadowRenderer(simpleApplication.getAssetManager(), 2048, 3);
        directionalLightShadowRenderer.setLight(directionalLight);
        directionalLightShadowRenderer.setShadowIntensity(0.3f);
        simpleApplication.getViewPort().addProcessor(directionalLightShadowRenderer);
    }

//    private void initPlayer() {
//        playerControl = new CharacterControl(new CapsuleCollisionShape((cubesSettings.getBlockSize() / 2), cubesSettings.getBlockSize() * 2), 0.05f);
//        playerControl.setJumpSpeed(25);
//        playerControl.setFallSpeed(20);
//        playerControl.setGravity(70);
//        playerControl.setPhysicsLocation(new Vector3f(5, terrainSize.getY() + 5, 5).mult(cubesSettings.getBlockSize()));
//        bulletAppState.getPhysicsSpace().add(playerControl);
//    }

    public static void initializeWater(SimpleApplication simpleApplication) {
        WaterFilter waterFilter = new WaterFilter(simpleApplication.getRootNode(), lightDirection);
        getFilterPostProcessor(simpleApplication).addFilter(waterFilter);
    }

    private static FilterPostProcessor getFilterPostProcessor(SimpleApplication simpleApplication) {
        List<SceneProcessor> sceneProcessors = simpleApplication.getViewPort().getProcessors();
        for (int i = 0; i < sceneProcessors.size(); i++) {
            SceneProcessor sceneProcessor = sceneProcessors.get(i);
            if (sceneProcessor instanceof FilterPostProcessor) {
                return (FilterPostProcessor) sceneProcessor;
            }
        }
        FilterPostProcessor filterPostProcessor = new FilterPostProcessor(simpleApplication.getAssetManager());
        simpleApplication.getViewPort().addProcessor(filterPostProcessor);
        return filterPostProcessor;
    }

    private void InitCubes() {
        blockManager = new MyBlockManager();
        cubesSettings = new CubesSettings(this);
        cubesSettings.setBlockSize(BLOCK_SIZE);

        //Failuer to do this next line results in an illeagal stateException:
        //no material is set for geometery.
        cubesSettings.setDefaultBlockMaterial("Textures/cubes/terrain.png");
    }

    private void AttachTestBox() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
    }

    private void SetupSky() {
        //https://code.google.com/p/jme3-utilities/wiki/AddToExistingGame
        timeOfDay = new TimeOfDay(12f);
        stateManager.attach(timeOfDay);
        timeOfDay.setRate(1000f);

        SkyControl sc = new SkyControl(assetManager, cam, 0.9f, true, true);
        //SkyControl sc = new SkyControl(assetManager, cam, 0.8f, true, true); //with star motion
        rootNode.addControl(sc);
        sc.getSunAndStars().setHour(12f);
        sc.getSunAndStars().setObserverLatitude(37.4046f * FastMath.DEG_TO_RAD);
        sc.getSunAndStars().setSolarLongitude(Calendar.FEBRUARY, 10);
        sc.setCloudiness(1f);

        sc.getSunAndStars().setHour(12f); //daylight

//        BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
//        bloom.setBlurScale(2.5f);
//        bloom.setExposurePower(1f);
//        Misc.getFpp(viewPort, assetManager).addFilter(bloom);
//        sc.getUpdater().addBloomFilter(bloom);

        sc.setEnabled(true);
    }

    /** A centred plus sign to help the player aim. */
    protected void initCrossHairs() {
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs

        int x = Math.round(settings.getWidth() / 2 - ch.getLineWidth() / 2);
        int y = Math.round(settings.getHeight() / 2 + ch.getLineHeight() / 2);
        ch.setLocalTranslation(x, y, 0);
        guiNode.attachChild(ch);
    }
    private Geometry mark;

    /** A red ball that marks the last spot that was “hit” by the “shot”. */
    protected void initMark() {
        Sphere sphere = new Sphere(30, 30, 0.2f);
        mark = new Geometry("BOOM!", sphere);
        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mark_mat);
    }
    //”returns the CollisionResults of the mouse cursor and a specified node”

    private CollisionResults getRayCastingResults(Node node) {
        Vector3f origin = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2), (settings.getHeight() / 2)), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2), (settings.getHeight() / 2)), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();
        Ray ray = new Ray(origin, direction);
        CollisionResults results = new CollisionResults();
        node.collideWith(ray, results);
        return results;
    }

    //” use the closest CollisionResult to receive the collisionContactPoint with
    //your BlockTerrain and to finally get the “pointed” block location”
    private Vector3Int getCurrentPointedBlockLocation(boolean getNeighborLocation) {
        CollisionResults results = getRayCastingResults(terrainNode);
        if (results.size() > 0) {
            Vector3f collisionContactPoint = results.getClosestCollision().getContactPoint();
            return BlockNavigator.getPointedBlockLocation(blockTerrain, collisionContactPoint, getNeighborLocation);
        }
        return null;
    }

    /** Declaring the "Shoot" action and mapping to its triggers. */
    private void initKeys() {
        inputManager.addMapping("Shoot",
                //new KeyTrigger(KeyInput.KEY_SPACE), // trigger 1: spacebar
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); // trigger 2: left-button click
        inputManager.addListener(actionListener, "Shoot");
        inputManager.addMapping("move_left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("move_right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("move_up", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("move_down", new KeyTrigger(KeyInput.KEY_LSHIFT));
        //inputManager.addMapping("jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "move_left");
        inputManager.addListener(actionListener, "move_right");
        inputManager.addListener(actionListener, "move_up");
        inputManager.addListener(actionListener, "move_down");
        inputManager.addListener(actionListener, "jump");
    }

    @Override
    public void onAction(String actionName, boolean value, float lastTimePerFrame) {
        if (actionName.equals("move_up")) {
            arrowKeys[0] = value;
        } else if (actionName.equals("move_right")) {
            arrowKeys[1] = value;
        } else if (actionName.equals("move_left")) {
            arrowKeys[3] = value;
        } else if (actionName.equals("move_down")) {
            arrowKeys[2] = value;
        } else if (actionName.equals("jump")) {
            playerControl.jump();
        }
    }
    /** Defining the "Shoot" action: Determine what was hit and how to respond. */
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Shoot") && !keyPressed) {
                /*
                 // 1. Reset results list.
                 CollisionResults results = new CollisionResults();
                 // 2. Aim the ray from cam loc to cam direction.
                 Ray ray = new Ray(cam.getLocation(), cam.getDirection());
                 // 3. Collect intersections between Ray and Shootables in results list.
                 rootNode.collideWith(ray, results);
                 // 4. Print the results
                 System.out.println("—– Collisions? " + results.size() + "—–");
                 for (int i = 0; i < results.size(); i++) {
                 // For each hit, we know distance, impact point, name of geometry.
                 float dist = results.getCollision(i).getDistance();
                 Vector3f pt = results.getCollision(i).getContactPoint();
                 String hit = results.getCollision(i).getGeometry().getName();
                 System.out.println("* Collision #" + i);
                 System.out.println("  You shot " + hit + " at " + pt + ", " + dist + " wu away.");
                 }
                 // 5. Use the results (we mark the hit object)
                 if (results.size() > 0) {
                 // The closest collision point is what was truly hit:
                 CollisionResult closest = results.getClosestCollision();
                 // Let’s interact – we mark the hit with a red dot.
                 mark.setLocalTranslation(closest.getContactPoint());
                 terrainNode.attachChild(mark); //attach to terrainNode instead of rootNode
                 } else {
                 // No hits? Then remove the red mark.
                 rootNode.detachChild(mark);
                 }
                 */
                //Get the free block gap, to which the user is loooking…
                Vector3Int blockLocation = getCurrentPointedBlockLocation(true);
                //(The block location is null, if the user looks in the sky or out of the map)
                if (blockLocation != null) {
                    //… and place a block there <img src='http://hub.jmonkeyengine.org/wp-includes/images/smilies/chimpanzee-smile.gif' alt=':)' class='wp-smiley' />
                    BlockBase block = MyBlockManager.GetInstanceByBlockID((short) 17);
                    blockTerrain.setBlock(blockLocation, block);
                }
            }
        }
    };

    private void initNewWorld(String seed, String path, String name) {
        Vector3Int numberOfChunks = new Vector3Int(4, 1, 4);
        blockTerrain = new BlockTerrainControl(cubesSettings, numberOfChunks);
        try {
            StandardWorld world = new StandardWorld(seed, path + FileSystemHelper.GetFileSeparator() + "Saves", name);
            int arrayBoundsX = 4;
            int arrayBoundsY = 4;
            Chunk[][] chunks = world.getChunkArray(Dimension.OVERWORLD, new Coordinate3D(0, 0, 0), arrayBoundsX, arrayBoundsY);
            AttachChunks(chunks, arrayBoundsX, arrayBoundsY);

            terrainNode = new Node();
            terrainNode.addControl(blockTerrain);
            rootNode.attachChild(terrainNode);
            log.log(Level.INFO, "Successfully created new world: " + name);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Could not create new world structure on disk. Exiting", ex);
            this.stop();
        }
    }
}
