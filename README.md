# Climate-Change-Analysis

## Project Overview

...

The project aims to visualize data using Java and JavaFX. Analyze climate data to study trends and potential environmental impacts, such as temperature changes, sea level rise, or extreme weather events. The data is sourced from a CSV file containing ~9000 lines of information collected during a specific month, August. The project's primary objectives are to section the data into multiple bins, create graphical representations based on the probability distribution at various points, and calculate the cumulative probability of data focusing on patterns of rain and inclement weather patterns.

### Key Features and Components:

Data Collection and Parsing: The project begins by retrieving the weather data from the CSV file and parsing it using Java to extract the necessary information.

Data Segmentation: The weather data is divided into multiple bins or categories, allowing for a more comprehensive analysis. This segmentation facilitates a better understanding of rainfall/other weather patterns based on various intensity levels throughout the focused region of data collection.

Probability Distribution Graphs: Using JavaFX, the project generates graphical representations of the weather data based on probability distributions. These graphs provide visual insights into the likelihood of specific rainfall values occurring within the given dataset.

Cumulative Probability Calculation: The project also involves calculating the cumulative probability of the weather data. This metric helps determine the likelihood of observing a certain amount of rainfall or less during the specified period. This information helps us predict future weather patterns in the same time frame and onwards.

User Interface: A user-friendly interface is implemented using JavaFX, enabling users to interact with the data visualizations, adjust parameters, and explore rainfall patterns effectively.


## Prerequisites
- Java JDK
- JavaFX
- CSV Data File (From REPO)

## Usage
1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/rohankumar009/RainFall-Analysis.git
   ```
2. Run the program:

## Contributing
Contributions are welcome, if you have suggestions, bug fixes, or improvements, please open an issue or submit a pull request!

## License
This project is licensed under the MIT License.
