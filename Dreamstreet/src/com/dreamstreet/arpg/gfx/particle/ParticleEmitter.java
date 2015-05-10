package com.dreamstreet.arpg.gfx.particle;

import com.dreamstreet.arpg.gfx.Camera;
import com.dreamstreet.arpg.gfx.Vector3;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created on 5/5/2015.
 */
public class ParticleEmitter {

    private ArrayList<Particle> particles;
    private Random rand = new Random();
    private int num;
    public ParticleEmitter() {
        particles = new ArrayList<>();
        num = 0;
    }

    public void bloodSpatter(Vector3 pos, Vector3 v) {
        float base_dx = rand.nextFloat() * 6 - 3;
        float base_dy = rand.nextFloat() * 6 - 3;
        for (int i = 0; i < (int)(Math.random() * 8) + 8; i++) {
            particles.add(new BloodParticle(new Vector3(pos.x, pos.y, pos.z), new Vector3(base_dx + rand.nextFloat() * 3 - 1.5, base_dy + rand.nextFloat() * 3 - 1.5, v.z)));
            num++;
        }
    }

    public void tick() {
        for (int i = 0; i < num; i++) {
            if (particles.get(i).getDuration() == 0) {
                particles.remove(i);
                num--;
                i--;
            }else{
                particles.get(i).tick();
            }
        }
    }

    public void draw(Graphics g, Camera camera) {
        for (int i = 0; i < num; i++) {
            particles.get(i).draw(g, camera);
        }
    }
}
