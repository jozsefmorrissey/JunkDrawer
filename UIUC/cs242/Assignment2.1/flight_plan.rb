class Flight_Plan

=begin
Continually asks for terminal until valid input is received
=end
  def get_metro(metros, type, diff)
    departure = ''
    while true
      printf "Enter the code for your %s airport\n", type
      departure = gets.strip!.upcase!
      if metros[departure] == nil
        puts "Invalid code try again\n"
      else if departure == diff
             puts "Please enter a metro that differs from you departure\n"
           else
             break
           end
      end
    end
    departure
  end

=begin
    prompts the user for departure and destination terminal
=end
  def prompt_user(airlineData)
    metros = airlineData.metros
    print_members(airlineData)
    departure = get_metro(metros, "departure", "")
    destination = get_metro(metros, "destination", departure)
    get_route(airlineData, departure, destination)
  end


  def get_route(airlineData, departure, destination)
    display_flight(shortest_path(airlineData, airlineData.metros[departure], destination, 0), airlineData)
    reset_flight_distance(airlineData)
  end

=begin
  resets FLIGHT_DISTANCE parameter of each node
=end
  def reset_flight_distance(airlineData)
    keys = airlineData.keys
    metros = airlineData.metros
    for key_index in 0..keys.size - 1
      metros[keys[key_index]][FLIGHT_DISTANCE] = nil
      metros[keys[key_index]][FLIGHT_PLAN] = nil
    end
  end

=begin
  displays flight info for a given list of "routes"
=end
  def display_flight(routes, airlineData)
    if routes != nil
      distance = 0
      time = 0
      cost = 0
      for index in 0..routes.size - 1
        if routes == nil or routes[index] == nil
          puts "nil"
        else
          route = routes[index]
          route_time, layover_time = calc_time(route, airlineData)
          route_cost = calc_cost(route, index)
          time = time + route_time + layover_time
          cost = cost + route_cost
          distance += routes[index].distance
          printf("%i)\t%s -> %s\t%-5i(km)\t", index + 1, route.departure_port, route.destination_port, route.distance)
          printf("Flight time: %-25sLayover time: %-25s\n", convert_hours(route_time), convert_hours(layover_time))
        end
      end
      printf("Total travel time: %-25s\tCost: %.2f\n\n", convert_hours(time), cost)
    else
      puts "No flight found"
    end
  end

=begin
  converts a floating point value of time to hours and minutes
=end
  def convert_hours(time)
    hours = time.floor
    minutes = (time - hours)*60
    minutes.round
    minutes = minutes.floor

    "#{hours} hours and #{minutes} minutes"
  end

=begin
  Calculates the in flight time as well as the layover
=end
  def calc_time(route, airlineData)
      half_dist = route.distance/2
      flight_time = 0
      if half_dist > 200.0
        flight_time = calc_acc_dec_time(200) + (route.distance - 400.0)/750
      else
        flight_time = calc_acc_dec_time(half_dist)
      end

      connections = airlineData.metros[route.departure_port][CONNECTION_LIST].size
      layover_time = 2.0 - connections/6.0

    return flight_time, layover_time
  end

  def calc_acc_dec_time(half_dist)
    (2.0*(Math.sqrt(4.0*half_dist*200.0/(750.0*750.0))))
  end

  def calc_cost(route, index)
    cost = (0.35 - 0.05*index)*route.distance
    if cost > 0
      cost
    else
      0
    end
  end

=begin
  Calculates the shortest path recursively form a metro to a given destination
=end
  def shortest_path(airlineData, metro, destination, distance)
    metros = airlineData.metros
    if metro[CODE] == destination
      return nil
    end
    traverse_graph(destination, metros, metro[CODE])
  end

=begin
  Marks nodes with their path distance
=end
  FLIGHT_PLAN = "flight_plan"
  FLIGHT_DISTANCE = "flight_distance"
  def traverse_graph(destination, metros, departure)
    explore_list = Array.new(0)
    metros[departure][FLIGHT_DISTANCE] = 0
    explore_list.push(metros[departure])
    min_final_distance = -1
    while explore_list.size > 0
      metro = get_closest(explore_list)
      route_list = metro[CONNECTION_LIST]
      follow_edges(destination, explore_list, metro, metros, min_final_distance, route_list)
    end
    #puts  "FIGHT_PLAN" + metros[destination][FLIGHT_PLAN]
    metros[destination][FLIGHT_PLAN]
  end

  def follow_edges(destination, explore_list, metro, metros, min_final_distance, route_list)
    for index in 0..route_list.size - 1
      route = route_list[index]
      dest_met = metros[route.destination_port]
      path_distance = metro[FLIGHT_DISTANCE] + route.distance
      if (dest_met[FLIGHT_DISTANCE] == nil or dest_met[FLIGHT_DISTANCE] > path_distance) and (min_final_distance > path_distance or min_final_distance < 0)
        explore_list.push(dest_met)
        dest_met[FLIGHT_DISTANCE] = path_distance
        if dest_met[CODE] == destination
          min_final_distance = path_distance
        end

        flight_plan = metro[FLIGHT_PLAN]
        update_destination(flight_plan, route, dest_met)
      end
    end
  end

  def update_destination(flight_plan, route, dest_met)
    if flight_plan == nil
      new_flight_plan = Array.new(0)
      new_flight_plan.push(route)
      dest_met[FLIGHT_PLAN] = new_flight_plan
    else
      new_flight_plan = copy_flight_plan(flight_plan)
      new_flight_plan.push(route)
      dest_met[FLIGHT_PLAN] = new_flight_plan
    end
  end

  def get_closest(explore_list)
    min = 0
    for index in 1..explore_list.size - 1
      if explore_list[index][FLIGHT_DISTANCE] < explore_list[min][FLIGHT_DISTANCE]
        min = index
      end
    end
    return explore_list.delete_at(min)
  end

  def copy_flight_plan(flight_plan)
    copy = Array.new(0)
    for index in 0..flight_plan.size - 1
      copy.push(flight_plan[index])
    end
    copy
  end

=begin
  Displays metros serviced to aid in selecting the desired destination and departure code
=end
  def print_members(airlineData)
    printed = Array.new(0)
    print = true
    count = 0
    metros = airlineData.metros
    keys = airlineData.keys
    airlineData.sort_alphabetically(keys)
    for key_index in 0..keys.size - 1
      metro = metros[keys[key_index]]
      for print_index in 0..printed.size - 1
        if metro == nil or printed[print_index] == metro[NAME]
          print = false
          break
        end
      end
      if print
        count += 1
        code = "(#{metro[CODE]})"
        printf "%-6s%-25s", code, metro[NAME]
        if count % 5 == 0
          puts
        end
      else
        print = true
      end
    end
    puts
  end

  def path_length(path)
    distance = 0
    for path_index in 0..path.size - 1
      distance += path[path_index].distance
    end
    distance
  end

  def initialize(airlineData)

  end

end