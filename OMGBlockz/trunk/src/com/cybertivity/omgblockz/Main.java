package com.cybertivity.omgblockz;

import com.cubes.*;
import com.cybertivity.omgblockz.blocks.*;
import com.cybertivity.omgblockz.worlds.*;
import com.cybertivity.omgblockz.dimensions.*;
import com.cybertivity.omgblockz.dimensions.Dimension;
import com.cybertivity.omgblockz.utility.*;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.*;
import com.jme3.bullet.control.*;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.*;
import com.jme3.input.controls.*;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.*;
import com.jme3.post.*;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.*;
import com.jme3.scene.shape.*;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.system.AppSettings;
import com.jme3.water.WaterFilter;
import java.awt.*;
import jme3utilities.TimeOfDay;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.logging.*;
import jme3utilities.sky.SkyControl;

public class Main extends SimpleApplication implements ActionListener {

    private static final Logger log = Logger.getLogger(Main.class.getName());
    private static final Vector3f lightDirection = new Vector3f(-0.8f, -1, -0.8f).normalizeLocal();
    private BulletAppState bulletAppState;
    private CharacterControl playerControl;
    private Vector3f walkDirection = new Vector3f();
    private boolean[] arrowKeys = new boolean[4];
    private CubesSettings cubesSettings;
    private BlockTerrainControl blockTerrain;
    private Node terrainNode;
    private static FileHandler fh = null;
    private static final int BLOCK_SIZE = 4;
    private MyBlockManager blockManager;
    private WorldInterface currentWorld;
    private DimensionInterface currentDimension;
    private BitmapText coordinatesText;
    private TimeOfDay timeOfDay;

    private static void configureLogging() {
        Logger l = Logger.getLogger("");
        fh.setFormatter(new SimpleFormatter());
        l.addHandler(fh);
        l.setLevel(Level.CONFIG);
    }

    public Main() {
        settings = new AppSettings(true);
        settings.setWidth(1680);
        settings.setHeight(1050);
        settings.setTitle("OMG! Blockz!");
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
        configureLogging();
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        initCubes();
        setupSky();
        //AttachTestBox();        
        initCrossHairs();
        initMark();
        initKeys();
        initializeEnvironment(this);
        //initializeWater(this);
        initBlockTerrain();

        Vector3f playerStartPos = new Vector3f(4f, 0f, 4f);
        int y = getTopMostEmptyBlock((int) playerStartPos.x, (int) playerStartPos.z, blockTerrain, currentDimension.getWorldHeight());
        playerStartPos.y = y + 5; //for a mini fall
        initPlayer(playerStartPos);

        //cam.lookAtDirection(new Vector3f(64 * BLOCK_SIZE, camHeight / 3, 64 * BLOCK_SIZE), Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(150);
        cam.setFrustumFar(4000);

        //setDisplayStatView(false);
        int camHeight = y + (1 //(to get on top of the ground) - I dont understand why I need this extra one...
                + 1); // (to get at eye level not feet level))
        Vector3f cameraLocation = new Vector3f(playerStartPos.x, camHeight, playerStartPos.z);
        cam.setLocation(cameraLocation);
        InitHud(cameraLocation);

    }

    private void initPlayer(Vector3f playerStartPos) {

        playerControl = new CharacterControl(new CapsuleCollisionShape((cubesSettings.getBlockSize() / 2), cubesSettings.getBlockSize() * 2), 0.05f);
        playerControl.setJumpSpeed(25);
        playerControl.setFallSpeed(30);
        playerControl.setGravity(70);
        playerControl.setPhysicsLocation(playerStartPos.mult(cubesSettings.getBlockSize()));
        bulletAppState.getPhysicsSpace().add(playerControl);
    }

    private void initBlockTerrain() {

        Vector3Int numberOfChunks = new Vector3Int(4, 1, 4);
        blockTerrain = new BlockTerrainControl(cubesSettings, numberOfChunks);
        terrainNode = new Node();
        terrainNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        terrainNode.addControl(blockTerrain);

        currentWorld = initNewWorld("TunaBomber", "D:\\temp\\OMGBlockz", "Test1");
        currentDimension = currentWorld.getDimension(Dimension.OVERWORLD);
        blockTerrain.addChunkListener(new BlockChunkListener() {
            @Override
            public void onSpatialUpdated(BlockChunkControl blockChunk) {
                Geometry optimizedGeometry = blockChunk.getOptimizedGeometry_Opaque();
                RigidBodyControl rigidBodyControl = optimizedGeometry.getControl(RigidBodyControl.class);
                if (rigidBodyControl == null) {
                    rigidBodyControl = new RigidBodyControl(0);
                    optimizedGeometry.addControl(rigidBodyControl);
                    bulletAppState.getPhysicsSpace().add(rigidBodyControl);
                }
                rigidBodyControl.setCollisionShape(new MeshCollisionShape(optimizedGeometry.getMesh()));
            }
        });

        rootNode.attachChild(terrainNode);
    }

    @Override
    public void simpleUpdate(float lastTimePerFrame) {
        float playerMoveSpeed = ((cubesSettings.getBlockSize() * 125.5f) * lastTimePerFrame);
        Vector3f camDir = cam.getDirection().mult(playerMoveSpeed);
        Vector3f camLeft = cam.getLeft().mult(playerMoveSpeed);
        walkDirection.set(0, 0, 0);
        if (arrowKeys[0]) {
            walkDirection.addLocal(camDir);
        }
        if (arrowKeys[1]) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (arrowKeys[2]) {
            walkDirection.addLocal(camDir.negate());
        }
        if (arrowKeys[3]) {
            walkDirection.addLocal(camLeft);
        }
        walkDirection.setY(0);
        playerControl.setWalkDirection(walkDirection);
        cam.setLocation(playerControl.getPhysicsLocation());
        coordinatesText.setText(playerControl.getPhysicsLocation().divide(BLOCK_SIZE).toString());
    }

//    @Override
//    public void simpleRender(RenderManager rm) {
//        //TODO: add render code
//    }
    private void setBlocks(short[][][] blockIDs, Coordinate3D startingCoordinates) {
        if (blockIDs != null) {
            //for every X,Z in chunk...
            Coordinate3D blockCoordinates = new Coordinate3D(0, 0, 0);
            for (int xOffset = 0; xOffset < blockIDs.length; xOffset++) {
                for (int zOffset = 0; zOffset < blockIDs[0][0].length; zOffset++) {
                    blockCoordinates.x = startingCoordinates.x + xOffset;
                    blockCoordinates.z = startingCoordinates.y + zOffset;
                    for (int y = 0; y < blockIDs[0].length; y++) {
                        BlockBase block = blockManager.getInstanceByBlockID(blockIDs[xOffset][y][zOffset]);
                        if (block != null) { //null=air
                            blockTerrain.setBlock(blockCoordinates.x, y, blockCoordinates.z, block);
                        }
                    }
                }
            }
        }
    }

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

    private void initCubes() {
        blockManager = MyBlockManager.getInstance();
        cubesSettings = new CubesSettings(this);
        cubesSettings.setBlockSize(BLOCK_SIZE);

        //Failuer to do this next line results in an illeagal stateException:
        //no material is set for geometery.
        cubesSettings.setDefaultBlockMaterial("Textures/cubes/terrain.png");
    }

    private void attachTestBox() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
    }

    private void setupSky() {
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

        inputManager.addMapping("move_left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("move_right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("move_up", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addMapping("move_down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("full_screen", new KeyTrigger(KeyInput.KEY_F11));
        inputManager.addMapping("Shoot",
                //new KeyTrigger(KeyInput.KEY_SPACE), // trigger 1: spacebar
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); // trigger 2: left-button click

        inputManager.addListener(this, "move_left");
        inputManager.addListener(this, "move_right");
        inputManager.addListener(this, "move_up");
        inputManager.addListener(this, "move_down");
        inputManager.addListener(this, "jump");
        inputManager.addListener(this, "full_screen");
        inputManager.addListener(actionListener, "Shoot");

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
        } else if (actionName.equals("full_screen")) {
            toggleFullscreen();
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
                    BlockBase block = blockManager.getInstanceByBlockID((short) 17);
                    blockTerrain.setBlock(blockLocation, block);
                }
            }
        }
    };

    private StandardWorld initNewWorld(String seed, String path, String name) {
        StandardWorld world = null;
        try {
            world = new StandardWorld(seed, path + FileSystemHelper.GetFileSeparator() + "Saves", name);
            Coordinate3D startPos = new Coordinate3D(0, 0, 0);
            short[][][] blocks = world.getTerrain(Dimension.OVERWORLD, new Coordinate3D(0, 0, 0), 16 * 21, 16 * 21);
            setBlocks(blocks, startPos);
            log.log(Level.INFO, "Successfully created new world: " + name);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Could not create new world structure on disk. Exiting", ex);
            this.stop();
        }
        return world;
    }

    private int getTopMostBlock(int xPos, int zPos, BlockTerrainControl blockTerrain, int worldHeight) {
        boolean foundBlock = false;
        int z = worldHeight + 1;

        while (!foundBlock && z > 0) {
            z--;
            foundBlock = blockTerrain.getBlock(xPos, z, zPos) != null;
        }
        return z;
    }

    private int getTopMostEmptyBlock(int xPos, int zPos, BlockTerrainControl blockTerrain, int worldHeight) {

        return getTopMostBlock(xPos, zPos, blockTerrain, worldHeight) + 1;
    }

    public void toggleFullscreen() {
        if (!settings.isFullscreen()) {
            GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            DisplayMode[] modes = device.getDisplayModes();
            int i = 0; // note: there are usually several, let's pick the first
            settings.setResolution(modes[i].getWidth(), modes[i].getHeight());
            settings.setFrequency(modes[i].getRefreshRate());
            settings.setBitsPerPixel(modes[i].getBitDepth());
            settings.setFullscreen(device.isFullScreenSupported());
            this.setSettings(settings);
            this.restart(); // restart the context to apply changes
        } else {
            //TODO: Remember previous settings and restore them.
        }
    }

    private void InitHud(Vector3f location) {
        coordinatesText = new BitmapText(guiFont, false);
        coordinatesText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        coordinatesText.setColor(ColorRGBA.Blue);                             // font color
        coordinatesText.setText(location.divide(BLOCK_SIZE).toString());             // the text
        coordinatesText.setLocalTranslation(10, settings.getHeight() - 10, 10); //hudText.getLineHeight(), 0); // position
        guiNode.attachChild(coordinatesText);
    }
}
