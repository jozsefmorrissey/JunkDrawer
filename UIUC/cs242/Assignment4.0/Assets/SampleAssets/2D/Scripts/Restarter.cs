﻿using UnityEngine;

namespace SampleAssets
{
    public class Restarter : MonoBehaviour
    {
        private void OnTriggerEnter2D(Collider2D other)
        {
            if (other.tag == "Player")
                Application.LoadLevel(Application.loadedLevelName);
        }
    }
}