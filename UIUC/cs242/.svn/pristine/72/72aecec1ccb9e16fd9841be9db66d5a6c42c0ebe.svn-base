class Edit_Data

  def initialize()

  end

  def get_user_input(airlineData)
    main_edit_menu(airlineData)
  end


  def main_edit_menu(airlineData)
    while true
      puts "Enter the metro code you would need to edit or destroy, otherwise\n1\tTo add a new terminal\n2\tRemove a terminal\n3\tTo add a route\n4\tRemove a route\n"
      input = gets
      if input == "\n"
        break
      end
      input.strip!
      case input
        when ""
          break
        when "1"
          add_new_terminal(airlineData)
        when "2"
          remove_terminal(airlineData)
        when "3"
          add_remove_route(airlineData, false)
        when "4"
          add_remove_route(airlineData, true)
        else
          if !check_terminal(input, airlineData)
            puts "metro not recognized"
          end
      end
    end
  end

  def check_terminal(term, airlineData)
      term.upcase!
      if airlineData.metros[term] != nil and airlineData.metros[term][CODE] == term
        edit_terminal(airlineData.metros[term], airlineData)
        true
      else
        false
      end

  end

  def display_attributes(metro)
    printf "Terminal code: %s\t\tCity Name: %s\t\tCountry: %s\t\tContinent: %s\n", metro[CODE], metro[NAME], metro[COUNTRY], metro[CONTINENT]
    printf "Timezone: #{metro[TIMEZONE]}\tCoordinates: %s \n", generate_coordinates_string(metro[COORDINATES])
    printf "Population: #{metro[POPULATION]} \tRegion: #{metro[REGION]}\n"
  end

  def generate_coordinates_string(coordinate)
      retVal = ""
      if coordinate["N"] != nil
        retVal = "((N,#{coordinate["N"]}),"
      else if coordinate["S"] != nil
             retVal = "((S,#{coordinate["S"]}),"
           end
      end

      if coordinate["E"] != nil
        retVal = retVal + "(E,#{coordinate["E"]}))"
      else if coordinate["W"] != nil
             retVal = retVal + "(W,#{coordinate["W"]}))"
           end
      end
    end

  def edit_terminal(metro, airlineData)
    run = true
    while run
      run = false
      printf "\nTo change an attribute of %s enter the attribute from the given list followed by the new value\n\n", metro[CODE]
      printf "List: %s, %s, %s, %s, %s, %s, %s, %s\n", CODE, NAME, COUNTRY, CONTINENT, TIMEZONE, COORDINATES, POPULATION, REGION
      puts "Format: 'attribute new-value'\n\n"

      display_attributes(metro)
      input = gets
      if input == "\n"
        break
      end
      first = input.dup
      first_command_terminator = input.index(" ")
      if first_command_terminator == nil
        printf "please enter a command fitting the given format"
        run = true
      else
        first_command = first.dup[0, first_command_terminator]
        second_command_terminator = input.index("\n")
        second_command = input.dup[first_command_terminator, second_command_terminator].strip!
        delegate_task(metro, first_command.downcase, second_command, airlineData)
      end
    end
  end

  def delegate_task(metro, first_command, second_command, airlineData)
    case first_command
      when CODE
        change_code(metro, second_command, airlineData)
      when NAME
        change_name(metro, second_command)
      when COUNTRY
        change_country(metro, second_command)
      when CONTINENT
        change_continent(metro, second_command)
      when TIMEZONE
        change_timezone(metro, second_command)
      when COORDINATES
        change_coordinates(metro, second_command)
      when POPULATION
        change_population(metro, second_command)
      when REGION
        change_region(metro, second_command)
      else
        printf "Attribute: %s not found\n\n", first_command
    end
  end

  def change_code(metro, new_value, airlineData)
    if new_value.size == 3
      rehash(metro, airlineData, new_value.upcase)
      metro[CODE] = new_value.upcase
      else if new_value.size < 3
        puts "This system was designed to accommodate codes of length 3\nIf you live in a word that has bee reduced to less then 1000 airports i am sorry for your loss but this program will need significant work to accommodate your needs\n\n"
           else
             puts "This system was designed to accommodate codes of length 3\nIf you have extended your service to offer flights to a larger system, congratulations but you will have to invest expanding this program\n\n"
           end
      end
  end

  def rehash(metro, airlineData, new_hash)
    modify_routes(metro,airlineData,new_hash)
    modify_keys(metro,airlineData.keys,new_hash)

    old = metro[CODE]
    airlineData.metros[old] = nil
    airlineData.metros[new_hash] = metro
  end

  def modify_routes(metro, airlineData, new_hash)
    metros = airlineData.metros
    keys = airlineData.keys
    for key_index in 0..keys.size - 1
      list = metros[keys[key_index]][CONNECTION_LIST]
      for list_index in 0..list.size - 1
        route = list[list_index]
        if route.departure_port == metro[CODE]
          route.departure_port = new_hash
        end
        if route.destination_port == metro[CODE]
          route.destination_port = new_hash
        end
      end
    end
  end

  def modify_keys(metro, keys, new_hash)
    for key_index in 0..keys.size - 1
      if keys[key_index] == metro[CODE]
        keys[key_index] = new_hash
      end
    end
  end

  def change_name(metro, new_value)
    metro[NAME] = new_value.split.map(&:capitalize).join(' ')
  end

  def change_country(metro, new_value)
    metro[COUNTRY] = new_value.upcase
  end

  def change_continent(metro, new_value)
    case_correct = new_value.split.map(&:capitalize).join(' ')
    if case_correct == AFRICA || case_correct == ASIA || case_correct == NORTH_AMERICA || case_correct == SOUTH_AMERICA || case_correct == AUSTRALIA  || case_correct == ANTARCTICA || case_correct == EUROPE
      metro[CONTINENT] = case_correct
    else
      printf "Sorry but your entry was not a continent at the time of the programs creation... not sure how that could change\n\n"
    end
  end

  def change_timezone(metro, new_value)
    timezone = new_value.to_i
    if -12 < timezone and timezone <15
      metro[TIMEZONE] = timezone
    else
      printf "%s is an invalid timezone\n", new_value
    end
  end

  def change_coordinates(metro, new_value)
    first_string = new_value.dup
    second_string = new_value.dup

    first_terminator = first_string.index(",")
    #second_terminator = second_string.index(-1)

    if first_terminator == nil
      print_coordinate_format
    else
      coordinate = Hash[]
      first_string = first_string[0, first_terminator]
      correct1 = read_coordinate_input(first_string.upcase, "N", "S", coordinate)
      second_string = second_string[first_terminator, second_string.length]
      correct2 = read_coordinate_input(second_string.delete(",").upcase, "E", "W", coordinate)

      if correct1 and correct2
        metro[COORDINATES] = coordinate
      end
    end
  end

  def read_coordinate_input(first_string, ne, sw, coordinate)

    direction = first_string.dup
    direction_terminator = direction.index(" ")
    degrees = first_string.dup
    degrees_begin = degrees.index(" ")
    direction = direction[0, direction_terminator]
    degrees = degrees.scan(/\d/).join('')
    degrees = degrees.to_i
    if 180 < degrees or degrees < 0 or (direction != ne and direction != sw)
      print_coordinate_format
      false
    else
      coordinate[direction] = degrees
      true
    end
  end

  def print_coordinate_format
    puts "Changing coordinates requires a special format: 'N/S (integer value),W/E (integer value)'"
  end

  def change_population(metro, new_value)
    pop_int = new_value.delete(",").to_i
    if pop_int >= 0
      metro[POPULATION] = pop_int
    else
      printf "If you ghosts or demons to be people only in a negative spectrum then perhaps you should find another source of employment\n"
    end
  end

  def change_region(metro, new_value)
    if new_value.to_i > 0
      metro[REGION] = new_value.to_i
    else
      puts "Please enter a positive number"
    end

  end


  def add_remove_route(airlineData, remove)
    departure = get_port("departure", airlineData.metros)
    destination = get_port("destination", airlineData.metros)


    if departure != "\n" and destination != "\n"
      if remove
          remove_route(airlineData, departure, destination)
      else
        distance = get_distance
        add_route(airlineData, departure, destination, distance)
      end
    end

  end

  def remove_route(airlineData, departure, destination)
    list = airlineData.metros[departure][CONNECTION_LIST]
    for list_index in 0..list.size - 1
      if list[list_index] != nil and list[list_index].destination_port == destination
        list.delete_at(list_index)
      end
    end
  end

  def add_route(airlineData, departure, destination, distance)
    route_hash = Hash[]
    ports = [departure, destination]
    route_hash["ports"] = ports
    route_hash["distance"] = distance

    require_relative 'route'
    route = Route.new(route_hash)
    airlineData.metros[route.departure_port][CONNECTION_LIST].push(route)
  end

  def get_distance
    while true
      puts "Enter the distance in nautical miles\nEnter nothing to exit\n"
      distance = gets
      if distance == "\n"
        return -1
      end
      distance = distance.to_i
      if distance <= 0
        puts "Please enter a positive number"
        else if distance > 22000
               puts "This distance indicates a flight more than halfway around the world, while impressive there is most likely a better flight plan"
             else
               return distance
             end
      end
    end

  end

  def get_port(type_port, metros)
    while true
      printf "Enter the code for the %s port\nEnter nothing to exit\n", type_port
      code = gets
      if code == "\n"
        return code
      end
      code.upcase!.strip!
      if metros[code] == nil
        puts "The code you have entered does not currently exist in this system, please add the terminal before trying to add a route\n"
      else
        return code
      end
    end
  end

  def add_new_terminal(airlineData)
    puts "Warning: All information must be correctly entered or the new metro will not be added"
    success, code, continent, coordinates, country, name, population, region, timezone = get_data(airlineData)
    unless success
      return false
    end

    set_new_terminal(airlineData, code, continent, coordinates, country, name, population, region, timezone)
    true
  end

  def set_new_terminal(airlineData, code, continent, coordinates, country, name, population, region, timezone)
    new_metro = Hash[]
    new_metro[CODE] = code
    new_metro[NAME] = name
    new_metro[COUNTRY] = country
    new_metro[CONTINENT] = continent
    new_metro[TIMEZONE] = timezone
    new_metro[COORDINATES] = coordinates
    new_metro[POPULATION] = population
    new_metro[REGION] = region

    airlineData.metros[code] = new_metro
    airlineData.metros[code][CONNECTION_LIST] = Array.new(0)
    airlineData.keys.push(code)
  end

  def get_data(airlineData)
    code = get_code(airlineData)
    if code == "\n"
      return false, false, false, false, false, false, false, false, false
    end
    name = get_name
    country = get_country
    continent = get_continent
    if continent == "\n"
      return false, false, false, false, false, false, false, false, false
    end
    timezone = get_timezone
    if timezone == -12
      return false, false, false, false, false, false, false, false, false
    end
    coordinates = get_coordinates
    if coordinates == nil
      return false, false, false, false, false, false, false, false, false
    end
    population = get_population
    if population == -1
      return false, false, false, false, false, false, false, false, false
    end
    region = get_region
    return true, code, continent, coordinates, country, name, population, region, timezone
  end

  def get_country
    puts "Enter country"
    retVal = gets
    retVal.strip!
    retVal.upcase!
  end

  def get_timezone
    while true
      puts "Enter timezone\nEnter nothing to exit\n"
      timezone = gets
      timezone = timezone.to_i
      if timezone == "\n"
        return -12
      end
      timezone.to_i
      if timezone < -11 or timezone > 14
        printf "%i is not a valid timezone, please enter an integer between and including -11 and 14\nEnter nothing to exit", timezone
      else
        return timezone
      end
    end
  end

  def get_coordinates
    dir1, dir2 = get_directions
    deg1 = get_degrees(dir1)
    deg2 = get_degrees(dir2)

    if deg1 != 360 and deg2 != 360
      coordinates = Hash[]
      coordinates[dir1] = deg1
      coordinates[dir2] = deg2

      coordinates
    else
      nil
    end

  end

  def get_degrees(dir1)
    while true
      printf "Enter the degrees in the %s direction\nEnter nothing to exit\n", dir1
      deg = gets
      if deg == "\n"
        retrun 360
      end
      deg = deg.to_i
      if deg < -180 or deg > 180
        puts "please enter a valid degree"
      else
        return deg
      end
    end
  end

  def get_directions
    dir1 = ""
    dir2 = ""
    run = true
    while run
      run = false
      puts "Enter the hemisphere (NW,NE,SW,SE)"
      hym = gets
      hym.strip!
      hym.upcase!
      case hym
        when "NW"
          dir1 = "N"
          dir2 = "W"
        when "NE"
          dir1 = "N"
          dir2 = "E"
        when "SW"
          dir1 = "S"
          dir2 = "W"
        when "SE"
          dir1 = "S"
          dir2 = "E"
        else
          puts "please enter a valid input"
          run = true
      end
    end
    return dir1, dir2
  end

  def get_population
    while true
      puts "Enter cities population\nEnter nothing to exit\n"
      pop = gets
      pop = pop.to_i
      if pop < 0
        puts "Please enter a positive value"
      else
        return pop
      end
    end
  end

  def get_region
    puts "Enter the region"
    region = gets
    region = region.to_i
    region
  end

  def get_continent
    while true
      puts "Enter the continent\nEnter nothing to exit"
      continent = gets
      if continent == "\n"
        return continent
      end
      case_correct = continent.split.map(&:capitalize).join(' ')

      if case_correct == AFRICA || case_correct == ASIA || case_correct == NORTH_AMERICA || case_correct == SOUTH_AMERICA || case_correct == AUSTRALIA  || case_correct == ANTARCTICA || case_correct == EUROPE
        return case_correct
      end
    end
  end

  def get_name
    puts "Enter cities name"
    name = gets
    name.split.map(&:capitalize).join(' ')
  end

  def get_code(airlineData)
    while true
      puts "Enter the 3-digit code for the metro\nEnter nothing to exit"
      code = gets
      if code == "\n"
        return code
      end
      code.strip!
      code.upcase!
      if code.length != 3
        puts "Please enter a code of the proper length\n"
        else if airlineData.metros[code] != nil
               puts "A metro is already registered with that code please, select a different code or edit/remove the other metro"
             else
               return code
             end
      end
    end
  end


  def remove_terminal(airlineData)
    puts "Enter the tree digit code for the metro you wish to remove"
    term = gets
    term.strip!
    term.upcase!

    if term.length != 3  || airlineData.metros[term] == nil
      return
    else
      printf "Are you sure you want to remove %s?(yes)\n", term
      yesNo = gets
      yesNo.downcase!
      if yesNo == "yes\n"
        rm_terminal(airlineData, term)
        puts "terminal removed\n"
      end
    end
  end

  def rm_terminal(airlineData, term)
    remove_routes(airlineData, term)
    remove_key(airlineData, term)
    airlineData.metros[term] = nil
  end

  def remove_routes(airlineData, term)
    metros = airlineData.metros
    keys = airlineData.keys
    for key_index in 0..keys.size - 1
      metro = metros[keys[key_index]]
      list = metro[CONNECTION_LIST]
      for list_index in 0..list.size - 1
        route = list[list_index]
        if route != nil and (route.destination_port == term or route.departure_port == term)
          list.delete_at(list_index)
        end
      end
    end
  end

  def remove_key(airlineData, term)
    keys = airlineData.keys
    for key_index in 0..keys.size - 1
      if keys[key_index] == term
        keys.delete_at(key_index)
      end
    end
  end

end