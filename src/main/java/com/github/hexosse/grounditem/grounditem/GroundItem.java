package com.github.hexosse.grounditem.grounditem;

/*
 * Copyright 2016 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.github.hexosse.grounditem.utils.JsonGroundItem;
import com.github.hexosse.pluginframework.itemapi.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

/**
 * This file is part GroundItemPlugin
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class GroundItem extends CustomItemStack
{
	// the ground item
	private Item groundItem = null;
    // Data used to create the groung item
    private ItemStack customItemStack = null;
    private Location location = null;

    /**
     * Constructs a new GroundItem at the given location
     * @param itemStack customItemStack to use as ground iten
     * @param location location of the ground item
     */
    public GroundItem(ItemStack itemStack, Location location)
    {
        super(itemStack.getType(), itemStack.getAmount());
        this.customItemStack = super.getItem();
        this.location = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
    }

    /**
     * Constructs a new GroundItem from an existing item
     * @param item item to use as ground iten
     */
    public GroundItem(Item item)
    {
		this(item.getItemStack(),item.getLocation());
		this.groundItem = item;
	}


    /**
     * Create the ground item
     *
     * @return true if creation succeed
     */
    public boolean create()
    {
		// Check if ground item already exist
		if(groundItem != null && (!groundItem.isDead() || groundItem.isValid())) return false;
		// Check if location is valid
		if(location == null) return false;
		if(Bukkit.getServer().getWorld(location.getWorld().getName()) == null) return false;
		if(!location.getChunk().isLoaded()) return false;
		// Check if Item Stack is valid
		if(customItemStack == null) return false;

		// remove existing groung item
        remove();

		// Create ground item
		groundItem = location.getWorld().dropItem(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ()), customItemStack);
		groundItem.setVelocity(new Vector(0, 0, 0));

        return true;
    }


    /**
     * Remove the ground item
     *
     * @return true if removal succeed
     */
    public boolean remove()
    {
        // Check if ground item exist
        if(groundItem==null) return false;

        // remove ground item
        groundItem.remove();
        groundItem = null;
        return true;
    }

    public Item getGroudItem()
    {
        return groundItem;
    }

    public ItemStack getCustomItemStack()
    {
        return customItemStack;
    }

    public Location getLocation()
    {
        return location;
    }

    @Override
    public final int hashCode()
    {
        int hash = 5;

        hash = hash * 17 + this.customItemStack.hashCode();
        hash = hash * 17 + this.location.hashCode();

        return hash;
    }

    @Override
    public final String toString()
    {
        final JsonGroundItem json = new JsonGroundItem(this);
        return json.toJson();
    }}
