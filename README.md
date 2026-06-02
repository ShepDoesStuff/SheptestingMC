# WaterLand Plugin

A Minecraft plugin that changes water physics to behave like land physics, allowing players to move through water with normal gravity and reduced drag. hi

## Features

- Toggle land physics while underwater
- Per-player settings
- Permission-based access control
- Smooth velocity modifications using Bukkit's task scheduler

## Installation

1. Build the plugin:
   ```bash
   mvn clean package
   ```

2. Place the generated JAR file in your server's `plugins` directory

3. Restart your server

## Commands

- `/waterland toggle` - Toggle WaterLand for yourself
- `/waterland on` - Enable WaterLand for yourself
- `/waterland off` - Disable WaterLand for yourself
- `/waterland <player> on` - Enable WaterLand for another player (requires `waterland.others`)
- `/waterland <player> off` - Disable WaterLand for another player (requires `waterland.others`)

Aliases: `/wl`

## Permissions

- `waterland.use` - Allows players to use WaterLand commands on themselves (default: op)
- `waterland.others` - Allows players to modify WaterLand settings for other players (default: op)

## How It Works

The plugin uses a repeating task that runs every tick (1L) to:
1. Check if the player is in water
2. Apply land gravity (0.08 downward velocity per tick)
3. Reduce water drag by multiplying horizontal velocity by 1.12
4. Disable the swimming animation

## Requirements

- Minecraft 1.21+
- Paper or compatible server (Spigot, Purpur, etc.)
- Java 21+
